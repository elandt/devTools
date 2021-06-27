# Course Notes

## Course Definitions (definitions from the perspective of the Spring Framework)

* POJO
  * Plain old Java object
  * Not simply an object with _only_ getters and setters
  * Objects that have both state and behavior
* JavaBeans
  * Objects with only getters and setters
* Spring beans ("Bean" throughout the course)
  * POJOs configured in the application context
* DTO
  * Data Transport Object
  * Object (often a JavaBean) used to move state between layers

## Inversion of Control (IoC)

* IoC container provides the mechanism for dependency injection
* ApplicationContext wraps the BeanFactory, which serves the beans to the runtime of the applicaiton
  * This is the piece that we configure when we configure Spring - the core of what we are working with
  * Serves as the IoC container
* Spring Boot provides auto-config of much of the ApplicationContext

## Spring Boot

* Why
  * Supports rapid dev
  * Removes boilerplate of application setup
  * Many uses
    * Web apps
    * Batch processing
    * Cron Jobs
    * And more
  * Cloud native support, but also traditional enterprise settings
* Key Components
  * Embedded Tomcat server (other options available)
  * Auto-config of ApplicationContext and component scanning
  * Auto servlet mappings
  * Embedded database support and hibernate/JPA dialect
  * Auto controller mappings

## Spring Data

* Why Spring Data
  * Provides a common set of interfaces for interacting with SQL or NoSQL data sources
    * Repositories
  * Provides a common naming convention for data access methods
  * Provides aspected behavior
  * Provides repository and data mapping conventions
    * Allows common ORM behavior
    * Mappers are dynamic and aspected
      * Can provide these if necessary
* Benefits
  * Removes boilerplate code
  * Provides a clean mechanism for swapping out data sources
    * Config changes rather than code changes
    * Example: switching from POC with h2 embedded db to production ready with a standard/remote DB
  * Allows for devs to focus on business logic
* Key Components
  * Repository interface
    * Primary contruct for interacting with data access across Spring Data
  * Entity Object
    * Provides data object for representing the table or document from the db
    * Repository relies on Entity for its template definition

## Understanding Dependency Injection

* IoC container allows for this to occur
* Why use dependency injection
  * Allows the dev to focus on contracts of your APIs
  * Develop business logic/code rather than managing the abstractions used in your code
  * Build intermediate abstractions
  * Promotes clean code
    * Less code to build/create objects and manage state
    * More code to handle business logic
* How Spring does Dependency Injection (DI)
  * IoC is configured by the developer(s)
    * Can be done with java config, component scanning with annotations, auto-config, or XML (shouldn't be used anymore)
    * Spring maintains handles to objects contructed at startup
      * Handles are maintained by the BeanFactory
    * Spring sserves singletons to classes during construction
      * Default behavior for Beans is to be singleton objects
    * Spring maintains lifecycle of Beans
      * Remain in memory until application quits
    * Dev only configures ApplicationContext

## Web Pages with Spring

* Model View Controller - MVC
  * Fundamental pattern for web app development
  * Allows for separation of concerns
  * Model is the data
    * Dynamic
  * View is the visual display that is populated
    * Static component
    * Often uses templating engines
  * Controller wires the view with the model
    * Selects the view, collects the data for the model, populates the model, then wires them together to display a visual to the user
* Spring Controller
  * Spring bean
    * Starts with a Pojo, then gets decorated with annotations to turn it into a controller
  * Annotated for the servlet mapping
    * Class level, and method level annotations are used to provide the needed behavior
  * Responds to incoming web requests
  * Outputs a view or raw data
* Template engines
  * Spring supports several
    * Thymeleaf - most popular
  * Provides DSL for HTML, leaving raw HTML documents
  * Placeholders for dynamic data
  * Rendering engine allows for final product
    * Merges the actual data with the placeholders to produce a web page

## RestController

* Spring uses controllers for RESTful web services
* Just another MVC, only our view is JSON
  * Don't need a model for your @GetMapping annotated methods
* Once you understand the paradigm, it's straightforward
* Spring marshals JSON for you
* Can configure XML if desired
  * Why would you want to
