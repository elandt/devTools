# Course Notes

## What is Spring

* Open Source
* Enterprise and internet support
* Lightweight
* Unobtrusive

### Spring in Enterprise

* Don't need heavy application servers
* Reduces boilerplate, allowing focus on business logic

### Dependency Management

* Spring manages runtime dependencies
* Configure or create them once

## Inversion of Control (IoC) Container

* Container maintains class dependencies
* Objects injected at runtime or startup time
* Dependencies can be injected via contructor, or setters
  * Field level injection is possible, but not recommended
  * If the class _requires_ a dependency, use constructor injection

### Benefits

* Reduces noise in code
* Reduces object coupling
* Reduces defects that arise from incorrect construction
* Focus on the API contract

## Application Context

* Application Context is the heart of a SpringApplication
* Encapsulates the BeanFactory
  * Provides the IoC Container
* Provides metadata for bean creation
* Provides the mechanism for handling ensuring beas are created in the correct order

### IoC Container

* Most beans are singletons and injected at startup, but some types of beans get handled differently
* All injections handled by BeanFactory
* Much of using Spring is the configuration of the IoC container
* BeanFactory handles all singelton beans, even if the object goes out of scope

### Multipe App Contexts

* Spring has at least one App Context, but can have more
* Web containers _always_ have multiple
* Parent can interact with child contexts, but (usucally) children can't interact with the parent

## Configuring

* Can use XML, but java is preferred
  * XML has been removed from most examples present on Spring.io
  * Benefits of Java config
    * Native language syntax
    * Compile time config checking
    * Easier IDE integration
      * Autocompletion
      * Syntax highlighting
### Environment

  * Populated by default with system environment variables
  * Supplement with properties files, among other things
    * Several layers of hierarchy - see Spring documentation for details
  * Powerful construct with cloud native and traditional applications

### Profiles

* Can be used to change behavior - for example, mocking in a QA environment

### Spring Expression Lauguage (SpEL)

* Ability to use expressions in config to configure objects
* Can be used to look at different files from the classpath based on an environment variable or some other value
* Uses `#{}` rather than `${}`

### Bean Scopes

* Singleton 
  * Default scope if none is specified
  * One instance per context definiton
  * Need to be mindful of state data
* Prototype
  * New instance everytime it's referenced
  * Definition stored in IoC, instances are not
  * Useful for transient data, or interface types that can flex based on app state
* Session
  * Similar to Prototype
  * Only applies in web environment
  * One instance per user session
  * Definition stored in IoC, instances are not
* Request
  * Similar to Prototype and Session
  * Only applies in web environment
  * One instance per request into the application
  * Definition stored in IoC, instances are not

### Proxies

* Aspected behavior applied to classes by the framework under various conditions
* In Spring, everything is a proxy (since Spring 4.0)
* Proxies add behavior
  * Many have specific uses that enable cleaner code; for example transational boundaries
* Spring uses JDK and CGLib proxies
* Considerations
  * Behavior added to classes only impacted by calls through the proxy
  * Private methods don't get exposed through the proxy
  * Internal calls also don't get proxy behavior
  * Example where this can be a potential problem is transaction rollback

## Component Scanning

* Spring Boot's auto-config is partially achieved through component scanning
* `@Component`
  * Indicates that a class should be loaded into the BeanFactory
  * Root annotation, but has sterotypes like `@Service`
    * Often sterotypes are used
  * Component scanning scans a base package (and that packages subpackages) and loads config automatically for each bean it finds
    * Uses other annotations to direct the IoC container to build the dependency graph
* Dependency Injection (DI) with Component Scanning
    * Achieved through component scanning
      * Mainly the `@Autowired` annotation
        * Instructs the IoC container to inject a bean into the needed value at that point
        * Can be added to a class attribute, but not recommended as it makes testing more difficult
        * Recommended uses (these aid in testing):
          * If the dependency is required - set annotation on the constructor
          * If the dependency is optional - the annotation should be on the setter
      * `@Qualifier` can be used if you have multiple beans of the same type
        * Allows injecting a specific version by name
        * Can be difficult to manage in an app, and may not be worth th effort
      * `@Value` is used to inject properties
* Starting Component Scanning
  * Requires a Java or XML configuration file (Java recommended)
    * With Spring Boot
      * auto-config is enabled through `@SpringBootApplication`
      * Does not _require_ a configuration file, though one can be used
  * Base package is defined in this configuration
  * Scanning occurs during startup
* Lifecyle Methods
  * In general, the system is not usable while Spring is starting
    * The same can occur during shutdown
  * Using Spring during Startup/Shutdown  
    * Spring Proxies not always available
    * Contstruction phase - the period of object instantiation where not everthing is available from a Spring perspective
    * Destruction phase - have no control over the order of garbage collection
    * Leverage JSR-250 annotations
      * These are not Spring specific
  * Post Contruction
    * `@PostConstruct`
      * Void method
      * No params
    * Allows for work to be done after the construction of an object
      * Example use: warming data caches
    * Called after property setting
  * Pre Destroy
    * Executed before the class is marked for garbage collection
      * Example use: capturing state before application closes, and store in a backing database
    * `@PreDestroy`
      * Void method
      * No params
    * Executed when ApplicaitonContext closes

## Bean Lifecycle

### Importance of Lifecycle

* Lifecycle - the various states your app goes through while being managed by the Spring Framework
* Knowledge of Lifecycle
  * Increases overall knowledge of Spring
  * Improves extensibility of the framework
  * Aids in troubleshooting
    * Because everything is a proxy, step-wise debugging is challenging
    * Better knowledge of the Lifecycle can lead to more educated and effective breakpoints
* Professional Development
  * Deeper knowledge of the tools we use - leads to being able to use them more efficiently and effectively
  * More educated discussions
  * Better architectural decisions
* Open Source
  * Better interactions with the community
    * Discussions
    * Better bug reports
  * Contributing to the framework

### Lifecycle Overview

* Three phases of the Lifecycle
  * Initialization
    * Most complex
    * Smallest amount of time spent here
    * We devs can impact the app behavior the most in this phase
  * Use
    * Largest amount of time is spent here
    * Most IoC container interaction is behind-the-scenes at this point
      * Mostly through proxies applied during initialization
  * Destruction
    * Occurs when ApplicationContext starts to close

#### Initialization

* Begins with creation of ApplicationContext
* Two inner phases
  * BeanFactory initialization phase
    * Bean definitions loaded
      * Sources of Bean Definitions
        * Java config (most different from the rest)
        * XML config (still valid, but no longer recommended)
        * Component scanning and auto config
      * Priming the BeanFactory
        * Bean definitions are loaded into BeanFactory from all sources
          * Don't need to use just one source, but recommended to pick one and stick with it
        * ID is used to create the index for the factory
          * ID only used for the index, otherwise referenced by type or name
        * BeanFactory only contains references at this point
          * Nothing has been instantiated yet
          * References to objects with metadata surrounding it for how to configure it
      * Phase completed
        * All beans have been loaded into the BeanFactory
        * Beans are just references and metadata (for now)
        * No object instantiation of code has occurred
    * Post-process bean definitions
      * Post-processrs are important set of behaviors that occur - all processing here happens before the objects are instantiated
      * Work performed is on the entire BeanFactory
        * Doesn't necessarily impact every bean
      * Can modify or transform any bean in the factory prior to instantiation
      * Most familiar example is the PropertySourcesPlaceholderConfigurer
      * BeanFactoryPostProcessor (BFPP) Interface
        * First point of extension in the lifecycle
        * Can write custom components to impact BeanFactory
        * Not common to write your own
        * Common to use existing ones - registering scopes, properties
        * BFPP and Java Config
          * Bean defs for a BFPP must be static
            * Removes risk of side effects that dynamic instances could produce
    * Phase Complete
      * Beanfactory is loaded with references to Beans
      * BeanFactory and all beans in it are configured
        * No further changes will happen to a bean until it's instantiated
      * All system-level work is completed in Spring at this point
        * From here until the application ends, is done on individual objects once created
  * Bean initialization and instantiation phase
    * Where objects are created
    * Steps (for each bean, in order)
      * Instantiate bean
      * Setters called
      * Bean post-processor (pre-init)
      * Initializer
      * Bean post-processor (post-init)
    * Construction
      * Beans instantiated in factory using constructors
      * Done in correct order to ensure dependencies are created first
        * Beans with the most dependencies are created last so that their dependencies would have already been created
      * Handle to class instance remains in the bean factory for the lifecycle of the application for _singletons_
        * Beans that have scopes other than Singleton are not constructed at this point in time
      * Eager vs Lazy
        * By default, all beans are instantiated eagerly
        * To be truly lazy, there can be _nothing_ that depends on it
        * You can specify a lazy bean, but the ApplicationContext can, and will, ignore it if necessary
          * ApplicationContext does this to prevent unintended consequences of not constructing the bean
      * Phase Complete
        * Bean pointer is still referenced in BeanFactory
        * Objects have been constructed
        * Objects are _not_ ready for use yet
    * Setters
      * Post-Initialization Dependency Injection
        * Can only be done on optional or dynamic beans
          * If it's required, it should be injected via constructor injection
            * The ApplicationContext and IoC container won't know optional vs required beans, so it's on you
            * Good development practice
        * Occurs after all beans have been instantiated
        * Setters are called
        * Autowiring occurs on all non-constructor methods that have `@Autowired`
          * If not using Java config, this is the only way
        * JavaConfig behaves differently
          * Everything needed is in the bean definition itself, including calling setters
      * Phase complete
        * Beans are fully initialized
        * All initial dependencies are injected
        * Beans still not ready for use
          * Work can still be done on them
    * Bean Post-Processing
      * Three pieces to it
        * Pre-init Bean Post-Processing
        * Initializer
        * Post-init Bean Post-Processing
      * Bean Post-Processing
        * Final point of config manipulation
        * Each bean may have additional behaviors added
        * Two types of extensible and generic processing
          * Before init
          * After init
      * Initializer
        * Second BeanPostProcessor action
        * Special case
        * `@PostConstruct` methods are called here
          * Spring has done essentially everything it needs to for you to use the Bean at this point
          * Still not in the use phase of the lifecycle though, i.e. application is not available yet
        * Framework provides many initializers
      * BeanPostProcessor Interface
        * The BeanPostProcessor interface allows you to inject common behavior to a "class" of beans
          * Behavior can be added to beans based on criteria
        * Still operates on specific beans, not the entire bean factory
        * Types: before and after
        * Framework leverages lots of these
          * Numerous proxies are build during the pre-init phase
      * Phase Complete
        * Beans have been instantiated and initialized
        * Dependencies have been injected
        * Beans are finally ready to use
        * All of this happens in a matter of seconds
* Differences based on configuration
  * Occurs mostly within the 'Instantiate bean' and 'Setters called' phases
  * Java Config
    * Instantiation and setter calling are merged into one phase
    * Each method with `@Bean` annotation is executed in proper order
      * Only work specified within the `@Bean` annotated method is executed during the bean lifecycle
      * If you don't put it in that method, it doesn't get done
    * Basically hands classes that are already ready to the BeanFactory
  * Autoconfiguration
    * Instantiation of all beans are scanned
    * `Autowired` constructors
      * `@Autowired` is optional if there's only one constructor
    * During setter injection, setters and fields with `@Autowired` are injected
      * Reminder, field-level injection is _not_ recommended
    * Spring scans and figures out everything that needs to be loaded and does work based on annotations
  * XML Configuration
    * Instantiation of all beans and constructor arg injection occurs during instantiation phase
    * Property injection
      * Can have P and C values
        * P injeciton occurs when setters are called
        * C injection occurs during instantiation
    * Similar in concept to Java config, but not handing the BeanFactory classes

#### Use

* Use Phase
  * Occurs after beans are initialized, configured, and instantiated
  * Most time spent here
  * ApplicationContext serves proxies to the original class
  * ApplicationContext maintains handle to each singleton bean
    * Singleton bean won't be garbage collected until the destruction phase
  * Context-Aware Beans
    * Spring provides interface ApplicationContextAware
    * Gives the class a handle to the ApplicationContext
      * Spring beans don't generally know they're in Spring, and they don't know their parent
    * ApplicationContextAware interface is not common to use, but available during Use Phase
      * Use with caution
      * Have access to call `context.close()` - kicks off the Destruction Phase

#### Destruction

* Destruction Phase
  * Begins when `close()` is called on ApplicationContext
  * Any method with `@PreDestroy` annotation is called
  * Beans are not destroyed - Java's garbage collectionn (GC) handles that piece
    * Destruction phase dereferences the beans, and they're marked as available for GC
  * Caveats
    * Context in not recoverable or reusable once `.close()` is called
    * Prototype beans are not impacted because they were available to GC once they instantiated and handed to the application
    * Only GC actually destroys bean instances

## Aspect-Oriented Programming

* Aspecting in Spring
  * What are Aspects
    * Reusable blocks of code that are injected into your application at runtime
    * Powerful tools for adding behavior
    * Solve cross-cutting concerns in one place
  * Common Applications of Aspects
    * Logging
    * Transaction management
      * `@Transactional`
    * Caching
      * Spring provides an annotation for this, but it may not be the best option for handling caching
    * Security
  * Why
    * Example need - logging routine that applies to every service method
      * Writing the code each time leads to code duplication - violates DRY principle
      * Mixes concerns - business logic with logging
  * Spring Aspects
    * Use AspectJ
    * Byte code modification (run time interweaving)
      * AspectJ can do other other methods of aspecting, like compile time interweaving
    * Dynamic proxy-based
      * Spring is all proxy-based
  * Parts of Spring Aspect
    * Join Point
      * Spot in the business logic where a cross-cutting concern can be applied
    * Pointcut
      * Routine used as the selection criteria to select the Join Point to apply the cross-cutting concern
    * Advice
      * Code applied to the Join Point when selected by the Pointcut
      * Applies cross-cutting concern logic
    * Aspect
      * The collection of the 3 above
    * Logging example
      * Logging routine is executed in Advice selected by a Pointcut to a Joint Point in the application code
  * Defining AOP Pointcuts
    * Selection has a syntax of its own
    * Pointcut Syntax
      * Format - `designator("r p.c.m(arg)")`
      * `r` - return type (can be wildcarded)
      * `p` - package (can be wildcarded)
      * `c` - class (can be wildcarded)
      * `m` - method (can be wildcarded)
      * `arg` - arg(s) - 0+
    * Common Designators
      * `execution` - expression for matching method execution
      * `within` - expressions for mating certain types
      * `target` - expressions for matching a specific type
      * `@annotation` - expressions for matching a specific annotation
        * Lean toward this one
          * It forces a conscious decision to be made to apply the annotaiton
          * Doesn't simply apply everywhere that matches a pattern
