# Course Notes

## What you Need/Need to Know

* Java
  * JDK 11 used in the course
* Spring
  * Basic Knowledge
  * Understanding value of Spring
  * Don't need to be an expert
* Maven
  * Used by instructor
  * I'm using Gradle
* Docker
  * Basic commands
  * Can choose to run databases/rabbit locally instead of running with Docker
* IDE
* Additional (useful, but optional) Software
  * Command line
  * HTTPie (can use cURL as well)
  * JSON Viewer (Chrome Plugin)

## Spring Boot Basics

* Spring Initializr is the most common way to start a new Spring app
* `@SpringBootApplication` annotation defines a class as the root package for configuration via component scanning as well as bean configurations that are defined within it
* `application.properties` can be used to load config props that you want stored _within_ the application itself, rather than injected via env vars
  * Could be more than just a properties file, could also use YAML
* The `contextLoads` test that is generated by the Spring Initializr loads the configuration, and ensures that the context loads so that everything is wired in
* Understanding Auto-Configuration
  * `@EnableAutoConfiguration`
    * Allows for config classes to be scanned dynamically
    * Often based on JARs in the classpath
    * Driven off of spring.factories
    * Order of auto-config can be controlled using the `@AutoConfigureBefore` and `@AutoConfigureAfter` annotations
    * `@EnableAutoConfiguration` is included by the `@SpringBootApplication` annotation (see JavaDoc for details)
    * Conditional Loading
      * `@ConditionalOnClass`
      * `@ConditionalOnBean`
      * `@ConditionalOnProperty`
      * `@ConditionalOnMissingBean`
    * Properties
      * Preconfigured defaul props for auto-config
      * `@EnableConfigurationProperties` specifies the default property set
      * Can always be overridden - check out the Spring documentation on priority of properties
    * Additional Conditionals
      * Application type based
      * Resource (or file) based
      * Expression based
* Configuring Spring
  * Property-Based Configs
    * application.properties or application.yaml
      * Basic config
      * Development focused
    * Env vars
    * Command-line params
    * Cloud configs (Config Server, Consul)
  * Bean Config
    * Adding beans to the default application class is an option, but can get unruly
    * Adding beans to separate config classes
    * Importing XML-based configurations
    * Component scanning
    * Can mix and match as needed
  * Spring Profiles
    * Why Profiles
      * Flex config based on environement profile
      * Valuable in real-world, multi-environment deployments
      * Live vs cold prod domains
    * Application.yaml
      * Spring Boot provides native .yaml support
      * Allows properties to flex by profile, among other things
      * `spring.profiles` is key for profile support
        * Indicates that the properties below it are specific to that profile
      * Other ways to trigger config based on profile
    * Enaging Profiles
      * `spring.profiles.active` env var
      * Inject via command-line or Environment
      * If a profile is specified as the active profile, but it has no definition, the default values will be used
        * Example: setting "prod" as the active profile, when only dev and test are defined
  * Building
    * Uses the build systems in place from the dependency management system (maven, gradle, etc)
    * Spring Boot comes packaged with scripts that call into the build and dependency management system
      * Check out Spring's documentation and examples
    * Building creates a jar or war, depending on the package type defined
      * Docker is an exception
  * Running
    * "Make JAR not WAR" - Josh Long
    * Best used as JAR files
    * Building a WAR is only necessary in "very few cases"
    * init.d or systemd registrations can be used to run Spring Boot apps on servers because the JARs have some prepended text that enables this
  * Containerizing
    * Built-in functions of build scripts
      * Maven: spring-boot:build-image
      * Gradle: bootBuildImage
    * Raw Dockerfiles
      * Benefits
        * More control
          * what's in the image
          * the lifecycle
        * Smaller images
          * Final image only uses only the JRE
        * Controlling the base image

## Spring Boot Web

* No difference between web apps (HTML based) and web services in the Spring Boot world
* Both use spring-boot-starter-web
  * What comes with spring-boot-starter-web
    * Includes Spring dependencies, and ones from vendors like Apache
    * Embedded Tomcat server
      * Can be replaced, but this is included out of the box
      * Very opinionated, default configuration
        * May not be the best option for real-world/production use
    * JSON Marshaller
      * Jackson
    * Logging
      * SLF4J
      * Built-in loggers
      * Property-based modifications
      * Logback logging
    * Spring Libraries
      * Spring Boot auto-config
      * Starters for Tomcat, logging, and Boot
      * AOP
      * core
      * context
      * beans
      * Web
      * webmvc
    * Others
      * SnakeYAML
      * Testing
* Configuring Embedded Tomcat
  * Servlets, Filters, Listeners
    * Default servlet responds at "/"
    * Beans of type servlet, filter or listener are scanned and loaded into the app context
      * Can annotate with @Bean at the method-level or annoate the class with @WebServlet, @WebFilter, or @WebListener
    * A web enabled Spring Boot app has several app contexts, including one for the embedded tomcat
    * Can create a separate ServletContext using ServletContextInitializer
  * Easiest way to configure is using properies
    * server.address, server.port, server.contextPath
    * Session based configs like cookies and timeouts
    * Error page path
    * Full list of properties can be found in the code at: `org.springframework.boot.autoconfigure.web.ServerProperties`
  * Compression
    * server.compression.enabled=true
      * Responses of 2048 bytes or larger are compressed by default when compression is enabled (this can be configured though)
      * Response types (MOME types) of text/html, text/xml, text/plain, and text/css will be compressed by default (can configure other types to be compressed as well)
  * TLS
    * Native support via properties
    * Most common embedded servlet change
    * Requires a Java keystore that contains the cert
    * Properties or env vars to configure
* Spring Boot Web Apps
  * Spring MVC
    * Default configs for MVC
    * Template engine starter for resolving views
    * Static file resolvers
  * Thymeleaf Templates
    * Valid HTML by itself
* Spring Boot Web Services
  * Still follow MVC, but with a twist, the view is the content type of the page rather than HTML
  * Swap out a web app's @Controller for @RestController
  * Serves JSON as that default output type
  

## Spring Boot command Line

* CommandLineRunner useful for some set amount of work that doesn't need web
* Can run as a standalone app, or inside another app
  * Think of admin or batch tasks/operations
* CommandLineRunner (CLR) Interface
  * Provides access to app args
  * Can run a simple or complex set of tasks
  * Uses a single run() method to implement
* ApplicationRunner (AR) Interface is similar to CLR
* Can package multiple in the same Spring package
  * Order them with @Order

## Spring Boot Data

* Repository Pattern
  * Use in Spring
    * Most of Spring Data is based on this pattern
    * Spring JDBC can be use to create dao actions
    * RepositoryRestResource addes a RESTful web service onto a raw repository
  * Pattern in a Nutshell
    * Introduced as part of domain-driven design
    * Simple ops of an entity or BO w/o knowledge of other entities (as in DAO)
    * This abstraction means that the data access operations are hidden from the user
  * Why
    * Dbs have become the bottleneck for many applications
    * Prevents data access logic from leaking into business logic
    * Adds a layer of abstraction
    * Focus on a single BO at a time
  * Creation Strategy
    * Start with repository interface in Spring Data (could create own with generics)
    * Define common data access methods
    * Apply business logic to a repository instance, and leverage the data access logic under the hood
* Spring Data
  * Spring Boot and Data
    * Support for both traditional RDBMS and NoSQL DBs
    * Based on the Spring Data project
    * Starters leverage common defaults
  * DB Drivers
    * Including a db technology lets Spring build a set of properties (these can be overridden)
    * Properties are used to supply creds and URLs for dbs
    * Certain dbs auto-configure and embedded db - fine for POC or demos, but not what you want for production
      * Leverage common scripts to "prime" embedded database
  * DataSources
    * With proper config, you get a DataSource object
    * Only one db is auto-configured
      * Multiple dbs are possible, but any after the first need to be manually configured
    * The DS can be injected if/where necessary
    * Spring Boot will configure repositories for Spring Data
  * Embedded Databases
    * Requires "schema.sql" and "data.sql" for "priming" the db

## Spring Boot Extra Topics

* Spring Security
  * In its simplest form, it provide basic auth for all endpoints except /js and /css
    * spring-boot-starter-security
    * Password is printed in log messages
    * Property-based config
  * Form-based auth supported, but requires config
    * Create extension of WebSecurityConfigurerAdapter
    * Use @EnableWebSecurity
    * Can be backed by a db or in-memory (should only be used for demos)
  * OAuth2
    * starter project for oauth2
    * Uses @EnableOAuth2Client
    * Can use @EnableAuthorizationServer, and customize it
    * Can use a ResourceServer with @EnableResourceServer
  * Passwords should be hashed, _not_ encrypted
    * SHA-1 should no longer be used in production
    * BCrypt is industry standard, and should be used for form-based authentication
* Spring Boot Messaging
  * Why Async Messaging?
    * Real-time processing is seldom a requirement
    * Reduces system strain
    * Improves efficiencies
  * Spring provides a producer template with default configs
    * Can modify with property changes
    * For RabbitMQ, provide exchange and queue name
  * Spring provides listener impls
  * Responds to messages on queue
* Spring Data REST Repositories
  * Code Example in room-web-app
* Spring Boot Actuator
  * Provides insights into running apps
  * Provides config settings, usually through JMX
  * Allows you to monitor running apps
  * Health
    * Up/Down status of app, and dependencies
    * App is only Up is deps are up
    * Great for liveness and readiness checks
      * Useful in Kubernetes in particular
  * Other endpoints
    * Beans
    * Env
    * Info
    * Metrics
    * Many others also available
  * Security and Customization
    * Locked down by default
    * Ensure the endpoints are not publicly available
      * Also a good idea to restrict internally as well
    * Only config what you need
    * Can be extended with custom endpoints
* Creating a custom Spring Boot Starter
  * Why
    * Common code/config/concerns across an enterprise
    * Improve ease of use
  * Library
    * Creating a starter is driven off a business or technical need
    * Isolate the code by function and build a library for it
    * Consider your dependencies wisely
  * Build the Auto-Config module
    * Can be included in the starter or isolated
    * Should not be included in your library, but should include it
    * Create config and config properties as needed
    * Contains the spring.factories file in META-INF
  * Build the Starter Module
    * Usually an empty JAR file
    * Dependency on auto-config module and library
    * Dependency on any other starter
      * For example, spring-boot-starter-web includes the tomcat and loggin starters
