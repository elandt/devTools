# Course Notes

## Intro

### What We Will Learn

* Foundations of Spring Performance
* Tools and techniques for performance monitoring
* Deep five into Spring hot topics
* A Spring professional's performance toolkit

### What you should know

* Core Java 8 - going to diverge from the course and use Java 25
* Core Spring and Spring Boot frameworks - this might get tricky due to using a newer Java version
* Familiarity with basic JVM, Hibernate, and AOP concepts
* Github/Git
* Maven-based project compilation - going to diverge from the course and use gradle
* Java-friendly IDE, such as IntelliJ, NetBeans, or Elcipse - going to use VS Code

### [My Course Files Fork](https://github.com/elandt/performance-spring)

Due to the default course files relying on https://quoters.apps.pcfone.io/api/random and the fact that it's no longer available, I've forked the [course files repo](https://github.com/LinkedInLearning/performance-spring-3158745) and then included the [quoters app](https://github.com/spring-guides/quoters) from the [Consuming a RESTful Web Service](https://spring.io/guides/gs/consuming-rest) as this appears to be what was previously hosted at https://quoters.apps.pcfone.io/api/random. In addition to including the quoters app (`spring-quoters-api`) in [my fork of the course files](https://github.com/elandt/performance-spring), I also added a devcontainer config so that various dependencies (Maven, VisualVM, Prometheus, etc) didn't need to be installed directly to my machine when they're only necessary for the course. With the addition of the `spring-quoters-api` and a small change to `DemoClientApplication.java` to point at it, the `demo-client` now builds and is runnable.

### Developer's Performance Toolkit

* JVM Profiling
  * VisualVM
    * Handy, popular profiler
    * Bundled with JDK through Java 8
    * Available as a stand-alone - [Github Repo](https://visualvm.github.io)
  * Java Flight Recorder
    * Component of JDK Mission Control
    * Handy for event capture and visualization
    * Available from 
      * [OpenJDK](https://openjdk.java.net/projects/jmc)
      * [Oracle](https://www.oracle.com/java/technologies/jdk-mission-control.html)
* Application Performance Monitor (APM)
  * [Glowroot](https://glowroot.org)
    * Lightweight
    * Open-source
    * Easy to run
    * APM with transaction reporting, trend reporting, and instrumentation capabilities
    * Requires `-javaagent` VM parameter on the application that you want to monitor
  * [Prometheus](https://prometheus.io/download)
    * Open-source
    * Can capture Spring Boot metrics trending over time
    * Requires configuration file
      * Does it actually with Spring Boot?
* Course setup suggestion
  * Have all 4 of the above tools in a `~/perf_utils` directory
  * In Intellij, setup the tools as external tools in the toolbar

## Foundations of Spring Performance

### Understanding performance

* How does your application perform?
  * Need to understand the underlying complexity
  * Every Spring app is a Java app, so being able to monitor with standard JVM tools can be highly beneficial
  * Spring provides internal performance reporting metrics to allow developers to gain insight into Spring specific information for their app
  * Spring apps are often composed of multiple, interconnected systems
    * Several of these may not be tunable by developers
    * External dependencies like the physical and virtual infrastructure are often among the pieces that are outside the developers control
  * Building your application profile
    * Proactive profiling allows you to support your Spring application in important ways:
      * Predictable growth and feature extension
      * Unpredictable stress conditions
  
### JVM Performance

* Understanding Spring app performance starts with the foundational JVM metrics
  * Some esstentials
    * JVM Heap
      * Where all objects are store - i.e. `new ...` lands on the Heap
      * Where garbage collection (gc) happens
        * GC = how Java reclaims the space on the heap for objects that are no longer needed by the app, and that not longer need to take up memory
    * CPU performance
      * Important to understand, but little that the developer can do to tweak the cpu

### Transaction Performance

* Application Runtime Monitoring (APM) is monitoring that provides a whole system view of your app performance
  * Request tracing
  * Database performance
  * Service call performance
  * Alerting
* Glowroot is an open-source APM
* Dynatrace is another option

### JVM Performance Challenge

* Force an OutOfMemory runtime error to be thrown by the DemoClient
* Then fix the error

## Spring Performance Monitoring

### Configuring Actuator

* Actuator is a utility for monitoring and managing the runtime operation of a Spring Boot app
* All endpoints (as of the time of the course creation) are enabled by default _except_ the `shutdown` endpoint
* You can enable/disable specific endpoints
* You can choose which endpoints are exposed in addition to being enabled
  * Endpoints can be exposed over HTTP or JMX (and then accessed through a Java management console)
    * JMX = Java management extensions
  * Security of these endpoints is important, but outside the scope of this course
* The Health endpoint can be customized
  * `management.endpoint.health.show-details=always`
  * `management.endpoint.health.show-components=always`
  * `management.endpoint.health.status.order=out-of-service,down,up`
  * `WeekendHealth.java` in `demo-api` is custom health indicator, more details in chapter 2 video 2

### Customizing Actuator

* Customizing can be done by extending the Spring Boot Actuate package classes
* `WeekendHealth.java` is a custom health indicator
  * Must implement `HealthIndicator`
  * Must be component scanned by Spring, easiest to use `@Component` for that
  * Must return a `Health` object
  * Can customize the health status ordering as well
* Can customize app info (`/actuator/info`) with simply `info.your.info.key=value` in your app properties
  * Can pull from your build config (at least with Maven) using `@key@`, for example, `@version@`

### Spring Actuator Micrometer Metrics

* [Micrometer](https://micrometer.io) provides an application metrics facade that supports numerous runtime monitoring systems, like Prometheus or DataDog
* Prometheus is what is used in the course
  * Specializes in
    * Fine-grained and customizable event alerting
    * Trending over time
    * Multi-application management
  * Available targets and other info configured in the `prometheus.yml` at the root of the 
* Categories of metrics
  * Counter
    * A count of something
    * Example: Uptime
  * Gauge
    * Current value, typically on a range
    * Example: CPU Usage
  * Timer
    * How long something takes, often relative to other actions in a series
    * Example: method timings, db calls, etc.
* Example custom metric: `WeekendGauge.java` in `demo-api`

### Customizing Spring Performance Logging

* Spring `PerformanceMonitorInterceptor`
  * Easy to configure, targeted performance monitoring
  * AOP
    * `org.springframework.aop.interceptor` package
    * Integrates with Log4j
    * Useful to track specific performance concerns
    * Advice
      * Bit of externalized behavior
    * Pointcut
      * Describes the point(s) where the advice is applied
    * Aspect
      * Class that declares the above two items
  * `PerformanceMonitorConfiguration` and `CustomPerformanceMonitorInterceptor` in `demo-client`
    * In order for the custom performance monitor interceptor to work, it must extend `AbstractMonitoringInterceptor`

### Actuator Challenge

* Expose one Spring Boot Actuator endpoint (health) from DemoApi as a JMX Bean to VisualVM

## Tuning for Common Spring Performance Challenges

### AOP Performance

* AOP introduces modularity into systems by means of the aspect
  * OOP does this by means of the object
  * AOP modularizes cross-cutting concerns such as logging and secuirty
  * In Spring, AOP is introduced using annotations
    * Spring AOP and/or AspectJ
* AOP combines Aspect code with "Native" code through "weaving" to create the running application
  * Type of weaving
    * Compile-time
    * Binary
    * Run-time
  * Key Questions
    * Does all this weaving negatively impact performance?
      * No, not for most Spring Applications
    * Do we need to care about it?
      * Yes, because it's hard to predict the real-world behaviors that our AOP configs will cause
* Observability
  * Instrumentation
    * Inject "hooks" into an app at compile time or runtim in order to debug or profile behaviors
    * Requires a Java agent to listen to the runtime and extract profile data
      * Glowroot is an option here
        * Some terms:
          * Transaction - a capture point boundary for a request or operation
          * Trace - detailed information about the operations within a transaction, such as timings, error call stacks, and thread profiles
          * Throughput - A measure of operation efficiency, often expressed as requests per minute 
        * Need to configure the Instrumentation in Glowroot in order to monitor
          * Name the class to monitor
          * Name the method to monitor
            * Can use wildcarding to monitor all methods within the class
          * Define the Trace entry
          * Name the timer (allow aggregation)
          * Define the Stacktrace threshold
          * Define the Transaction type
            * Shows up in the type drop down menu on the Transactions page of Glowroot
          * Name the Transaction

### Hibernate Performance

* Hibernate - prevalent Object Relational Mapping (ORM) framework for database connectivity within the Spring landscape
  * DB interactions are often the most resource-intensive components of a Spring app
  * Key design decisions
    * Connection pooling
      * Saves the expense of needing to make a new db conneciton every time you need data
      * Connections are established, then used from and returned to the pool as needed
    * Caching
      * Reusing data for a period of time to save on expensive db queries
        * L1 - session scoped, enabled by default
        * L2 - applicaiton scoped, must explicitly enable
          * Some of the important config considerations
            * Concurrency, ro vs rw
            * Size (number of entries)
            * TTL
            * Memory alloted
        * Use `@Cacheable` to make entities cacheable
    * Lazy loading
      * Feature in which Hibernate delays data fetching until the runtime requires use of an object
        * Preferable in most cases, and especially important for n-to-many relationships
    * Observability
      * `logging.level.org.hibernate.SQL=DEBUG` sets the logger low enough to be able to see the executed SQL statements
      * While you're at it, turn on `spring.jpa.properties.hibernate.format_sql=true` as well
      * Can enable `spring.jpa.properties.hibernate.generate_statistics` as well, but this is expensive, and should not be done in production
      * Using `hibernate-micrometer` dependency, you can get a lot more metrics from hibernate that can then be analyzed with something like Prometheus

### App Start-up Time

* App runtime start-up for Spring apps involves a lot of activities, and sometimes slow start-up time can inhibit success
  * When does start-up time matter?
    * CI/CD automated testing
    * Local developer convenience
    * General indicator of bloated dependencies
  * Context and bean loading is most of the challenge with start-up time
  * Observability is key to being able to address this
    * Actuator metrics
      * `/actuator/startup` shows details of the app startup, but by default is not the most readable
        * Can apply filters to the `BufferingApplicationStartup` to reduce the number of events tracked and viewable at the actuator endpoint
    * Java Flight Recorder event capturing

### Spring Boot Start-up Challenge

* Modify the DemoClient app start-up config to report only the start-up events that are initiated by the Spring Boot framework
* `bas.addFilter(t -> t.getName().startsWith("whatever.you.want"))`

## The Spring Developers Performance Toolkit

### Compiling a Performance Profile

* Performance Profile as Living Documentation
  * Capture impactful decision and decision tree
  * Surfaces decision impacts to performance trends over time
  * Leads to informed conversations over the lifecycle of the app
* Describe your application in words and as a component diagram
* Break out metrics into two categories
  * JVM metrics
  * Connected components
* Start describing loads under which you need to operate
* Capture baseline metrics
* Can then identify areas of concern
* `./demo-docs/Application Performance ProfileTemplate.pdf` is a template that can be used for documenting an app's performance profile

### Presenting your work

* App performance is nuanced, multi-faceted, and dependent on many variables
* Avoid (very) technical jargon
  * Compare these two sentences on the same topic
    * Our API responsiveness has degraded because the logging aspect is joined to a high volume of HTTP response payloads.
    * We can improve our API responsiveness by reducing our logging activity.
* Use pictures and graphs
  * Adds visual backing to statements being made
  * Shows you've done the necessary measuring
* Summarize what is important and be prepared to go deeper
* Equip the audience to ask more questions by explaining trade-offs.
  * Our API responsiveness has slowed by 26% over the last 6 months.
    * A factual statement, but what is the audience supposed to do with that? What are you suggesting?
  * We can solve our slower API response times with more network throughput or by splitting our API into smaller microservices.
    * Providing options invites questions

## Next Steps

* Other LinkedIn Learning courses
  * Java Database Access with Hibernate
  * Java Memory Management
* Spring Documentation
  * Actuator
