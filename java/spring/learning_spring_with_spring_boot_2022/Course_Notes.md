# Course Notes

## Definitions

* See definitions listed in java\spring\learning_spring_with_spring_boot\Course_Notes.md

## Auto Config

* Default opinionated configuration (common options)
* Easy to override defaults usually using associated properties
* Configuration on presence, i.e. only when a given thing is present

## Inversion of Control

* Container maintains your class dependencies
* Objects injected at runtime or startup
  * Depending on how they're defined in the class
  * Generally injected at startup when added to the bean factory
* An object accepts the dependencies for contruction instead of contstructing them

### Traditional Dependency Management

* Example
  * Class with `main()` method
    * Two classes needed by `main()`
      * One of those classes needs one more class
        * That class needs two more classes
* In this example
  * Five classes are created out of the gate
  * These classes go three levels deep
  * That's a lot that needs to be known up front to configure and manage these classes on startup

### IoC Dependency Management

* Starts with an IoC Container (generally triggered from `main()`)
* IoC Container builds itself specific objects needed and referenced by `main()`
* The underlying dependencies are created, but are simply pointers to those classes
* IoC Container handles injection of those dependencies into their parent class
* No longer need to know how to construct things three layers deep - IoC Container manages the life cycle of those dependencies

### Spring IoC

* IoC Container = Bean Factory
  * Don't often, if ever, interact directly with Bean Factory
* Do interact with the Application Context
* References - not handled by the Bean Factory
* Beans instantiated in proper order
  * Will be warned about circular dependencies

## Annotations

* Native support in Java
  * Not a Spring specific item
* Metadata for your code
* Often used for compiler or runtime intructions
  * ex: `@Test`
* Great leverage point for pointcuts
* Proxies
  * Beans in Bean Factory are proxied
  * Annotations drive proxies
  * Annotations are easy extension points, for your own abstracts too
  * Proxied classes need to be called through the proxy itself
  * Method calling order matters

## Spring Data

* For additional notes see: java\spring\learning_spring_with_spring_boot\Course_Notes.md
* Uses JPA and Hibernate
* Key Components
  * DataSource
    * Not usually accessed in code unless using JDBC Templates