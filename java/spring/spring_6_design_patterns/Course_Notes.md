# Course Notes

## What you Need/Need to Know

* Java
  * Solid Java Knowledge
  * JDK 17
  * Baseline Java Knowledge
* Spring
  * Basic Understanding of Spring
  * LinkedIn Learning: _Learning Spring with Spring Boot_ by Frank P. Moley III
* Object-Oriented Programming (OOP)
  * Strong OOP conceptual background
  * Knowledge of basic design patterns is helpful

## What Design Patterns are

* Design Patterns
  * Best-practice solutions to common problems
  * Designed with good OOP in mind
  * Typically presented as Problem + solution + application of pattern to solve the problem
  * Often prescribe how/when to use them
* Why do we care?
  * Patterns provide common solutions to common problems
    * Why recreate the wheel
  * Use patterns over and over again
    * Efficiency as you get better at this
  * Proven implementations to leverage
    * Saves effort on innovation and experimentation that could be used more meaningfully elsewhere
  * Industry has adopted patterns
    * Can be useful in improving quality and efficiency
* Value for Developer
  * Trusted and well-tested patterns makes solutions easier
    * Reduces effort for testing and maintaining
  * Faster to market
    * Because you don't have to reinvent the wheel
  * Easier to explain your code
  * Easier to for future you to read code later

## Design Patterns and Spring

* Gang of Four
  * Who
    * Erich Gamma
    * Richard Helm
    * Ralph Johnson
    * John Vlissides
  * They were Computer Scientists and author
  * OG book: Design Patterns: Elements of Reusable Object-Oriented Software
    * Talks about the good and the bad of OOP
    * Focus on common OOP concerns
    * Emphasis on design patterns
* Pattern Categories
  * Creational
    * Create objects for you
    * Examples
      * Abstract Factory
      * Factory
      * Builder
      * Singleton
      * Prototype
  * Behavioral
    * Focus on inter-object communication
    * Examples
      * Command
      * Interpreter
      * Mediator
      * Iterator
      * Observer
      * Chain of responsibilities
      * Visitor
      * Memento
      * State
      * Strategy
      * Template
        * Used all over the place in Spring
  * Structural
    * Focus on object composition and interfaces
    * Examples
      * Adapter
      * Bridge
      * Composite
      * Decorator
        * Often called the "Annotation" pattern today
      * Flyweight
      * Facade
        * Often used with service abstraction
      * Proxy
        * Also used all over the place in Spring
* Relation to Spring
  * Inherently uses many of these classic patterns
  * Provides native support for using some of these in your code
  * Java is an object-oriented language
* Patterns of the Spring Framework
  * Patterns in Spring
    * Spring is based on common design patterns
    * Play a role in almost every aspect of the framework
    * Framework uses pattern
      * Supports use of design patterns
    * Inversion of Control Pattern
      * The big one
      * Entire runtime of framework based on inversion of control (IoC)
      * Benefits
        * Improved testability
        * Decreased coupling
        * Enforced coding to interface
    * Proxy Pattern
      * Since Spring 4.0, every object is proxied
      * Brings some caveats to the operation of Spring
      * Allows additional behaviors to be leveraged throughout the framework
    * Factory Pattern
      * IoC container is itself a factory
      * Heavily leveraged in startup and runtime operations
      * First-class support for factories in code
    * Singleton and Prototype Patterns
      * Most objects configured for Spring leverage these
      * Singleton is most popular in the default behavior
      * Not a traditional singleton implementation, but works the same
    * Template Pattern
      * Used often in remote calls
        * JDBC
        * Rest
        * Mongo
      * Implemented by third parties to remove boilerplate code for using their tooling
    * Model-View-Controller Pattern (MVC)
      * Entire web framework based on it
      * Traditional web pages
      * RESTful services
      * Not a classic pattern, but very important in Spring
* IoC
  * What is IoC
    * Not a Gang of Four design pattern
    * Architectural pattern for relegating control of dependencies to the container instead of the developer
    * Often called dependency injection, though that is one flavor of IoC rather than IoC itself
  * How it works
    * Central container constructs and maintains all object references
    * Hands the object references to other objects when needed at runtime (or startup)
    * Centralized control and management - specifically around object references and lifecycle
  * IoC in Spring
    * Spring manages your dependencies
      * Don't try to mix IoC with `new` - once you use `new` to create an object, Spring is not managing that dependency
    * Objects are injected at runtime, not compile time
    * An object accepts the dependencies for constructuion instead of you constructing them
      * The handle to the creation is maintained by the IoC container (in this case, the bean factory)
  * Benefits of IoC
    * Reduces noise in your code
      * Don't have `new` scattered around, and needing to pass objects around by hand
    * Reduces object coupling
      * You start coding to interfaces rather than the concrete implementations
    * Reduces the defects that arise from incorrect construction
      * Copy/Pasta of object construction that doesn't quite fit the new use case
      * Memory issues - references
  * `ApplicationContext`
    * Spring's `ApplicationContext` is the IoC container
      * This is what we, as developers, actually interact with (as a wrapper to the IoC container)
    * Another pattern in play (later discussed) is the Factory Pattern with the `BeanFactory`
    * When talking about the `ApplicationContext` it could be one of two flavors, from the IoC context or from the fact that it's a factory
    * Leverages the configuration to create beans and manage the dependency injections
      * Auto-config
      * Component scanning
      * Java config
      * XML (legacy - still supported, but not advised)

## Creation Patterns in Spring

* Factory Pattern
  * Use in Spring
    * `BeanFactory` - core of the IoC container
    * `FactoryBean` - generic or templated interface that is used to encapsulate object construction logic that has special considerations or is non-static
    * Leveraged heaviliy in the framework
  * Pattern in Brief
    * Allows construction of similar classes of different types using factory method
    * Method call creates object for you and serves it back
    * Constructed objects are from classes that share interface or parent class
      * Example
        * Pet
          * Canine
          * Feline
  * Problems to Solve with Factory Method
    * You don't need to worry about class construction in more than one place
    * Leverage interface for repetitive operations
    * Lack of object construction cleans up code
    * Makes copy/pasta errors less likely
  * Strategy to Create Factories (outside of Spring)
    1. Always code to an interface (Not doing so misses out on much of the value of the factory)
    2. Create the common interface
    3. Create one or more classes with instances of the interface
    4. Implement concrete classes from the interface
  * Abstract Factory Pattern
    * Take the pattern one step further
    * Becomes a factory of factories
    * Adds powerful level of abstraction if you have a bunch of types with a bunch of types
      * For example, thinking of pets again
        * Pet
          * Canine
            * Beagle
            * Labrador Retriever
            * Poodle
          * Feline
            * American Shorthair
            * Maine Coon
            * Siamese
            * Sphynx
  * In Action
    * Packages
      * `factory`
* Builder Pattern
  * Builder Pattern in Spring
    * Used throughout the framework
    * `ResultActions` - part of MockMvc
    * Lombok `@Builder` annotation - not really Spring but embraced
  * Pattern in Brief
    * Each method returns the object reference it's called from
    * Build method returns the fully constructed object
    * Functionality can be combined into one clase
      * Usually not done that way since it often requires statics
  * Problem Statement
    * Useful when object creation has many parameters
    * Becomes more useful when some or all parameters are optional
    * Can make code easier to read
      * Reduced lines for construction compared with setters...but chaining methods can be harder to read at times
  * Creation Strategy
    1. Start with base class with all args constructor
    2. Create builder class with attribute and setter for each base class argument (each setter should return builder).
    3. Create build method that will construct and return base class
  * In Action
    * Packages
      * `builder`
* Singleton Pattern
  * There's a deviation between the Singleton pattern in Java vs the Singleton pattern in Spring
    * This is because of the IoC container
  * Singleton Pattern in Spring
    * Every bean by default is a singleton
      * Be cautious of state on beans (think thread safety)
    * Not a classic singleton, but behaves the same
    * Beans must be thread safe
  * Pattern in Brief
    * Class stores reference to instance of itself
      * This is how the more traditional Java singleton apparatus is maintained
    * Contructor is private
    * Static `getInstance` method returns reference of self.instance
      * If not constructed, constructs an instance is a thread-safe manner
    * `new` is never called on the class outside of the `getInstance` method
    * Problem Statement
      * Expensive object creation
        * DB handles/connections for example
      * Control concurrency-associated shared resources
      * Storing static state for multiple parts of the application
    * Creation Strategy
      * In Java
        1. Create a private constructor
        2. Instance handle
        3. Static `getInstance` method that synchronizes on the object to reduce thread safety risks
        4. Create instance if it doesn't exist
    * In Action
      * Packages
        * `singleton`
        * Traditional Java --> `SingletonA`
        * Spring --> `SingletonB`
    * Tests
      * `SpringDemoApplicationTests.testSingleton`
* Prototype Pattern
  * Protoype Pattern in Spring
    * Beans have to be marked as `prototype`
      * They'll be treated as `Singleton` otherwise
      * Similar difference between native Java and Spring as with the Singleton pattern
    * Bean configuration is used as the prototype
      * You don't actually create an instance
    * New instances are created, not cloned, when needed by runtime
      * This differs from traditional Java where they would be _cloned_
  * Pattern in Brief
    * Class is created in a prototypical manner with template
    * Instance is cloned (traditional Java) at runtime to get new instances that aren't the prototype
      * Act the same and have same state
    * In Java, this is usually done with the `Cloneable` interface
    * Prototypes are usually deep clones of object
      * Maintains safety
  * Problem Statement
    * When object creation is expensive but wrapper needs to be unique
      * i.e. there is state, but that state needs to be uniquely maintained
    * With objects that must act thread safe but need to store state
      * The state stored may not necessarily be thread safe itself
    * Cost savings on object creation
  * Creation Strategy
    * In Java
      1. Create abstract base class that implements `Cloneable`
      2. Extend that class with prototypes
      3. Override clone method to provide class-specific behavior
      4. Clone sensitive sub-objects to prevent threading issues
  * In Action
    * Packages
      * `prototype`
    * Tests
      * `SpringDemoApplicationTests.testPrototype`

## Structural Patterns in Spring

* Adapter Pattern
  * Adapter Pattern in Spring
    * Integration with Channel Adapters for communication with different systems
    * Internal operation of AspectJ and used during load
  * Pattern in Brief
    * Two different interfaces share a common operation
    * It's a wrapper class that holds an instance of one interface and implements the other
    * Shared operation of the wrapper interface is implemented to call the shared operation of wrapped interface
  * Problem Statement
    * Legacy or 3rd-party code that needs to fit into your code
    * Shared functionality on disparate objects to reduce code
    * Coding to interfaces in routine workflows
      * Leveraging adapters reduces code while improving readability
  * Creation Strategy
    1. Two interfaces that share a method
    2. Implement both with concrete classes
    3. Create a wrapper class that extends one and has an instance of the other
    4. Implementation in adapter calls instance method
  * In Action
    * Packages
      * `adapter`
    * Tests
      * `SpringDemoApplicationTests.testAdapter`
* Decorator Pattern
  * Decorator Pattern in Spring
    * Framework uses decorators
    * Injecting decorated objects is difficult because of how bean references work
    * Use of `qualifier` annotation required
      * Spring only allows instances of a class once _unless_ you qualify it then inject it
  * Pattern in Brief
    * Adds responsibilities to an object dynamically at runtime
    * Promotes composition instead of inheritance
      * By decorating, you are adding or composing properties to it rather than inheriting from something
    * Inherited base classes compose new behavior and responsibility
      * Become additive or decorated
    * Lets an object be open for extension and closed for modification while adding responsibilities
  * Problem Statement
    * Promote composition over inheritance
    * Add behavior without modifying code
      * Support non-breaking changes
    * Remove behavior through encapsulation via decoration
  * Creation Strategy
    1. Base abstract class
    2. Build Decorator abstract class extending base class
    3. Build decorators that extend Decorator abstract class
    4. Decorators maintain instance of base abstract class with constructor injection
  * In Action
    * Packages
      * `decorator`
    * Tests
      * `SpringDemoApplicationTests.testDecorator`
* Proxy Pattern
  * Proxy Pattern in Spring
    * Every bean you create gets a proxy around it (since Spring 4)
    * Proxies usually added through annotation
    * Creating proxies usually revolves around aspect-oriented programming (AOP)
  * Pattern in Brief
    * Uses an intermediary object in place of a real one
    * Intermediary protects real object
      * Adds value to it
      * Controls instantiation and access
      * Adds behavior to real object
  * Problem Statement
    * Expensive operations need to be protected and called only when needed
      * `@Transactional` as an example
    * Add behavior to a method when called in specific situations
      * May not always need the behavior added
    * Remote object access
  * Creation Strategy
    1. Start with an interface
    2. Create real object to extend interface
    3. Create proxy object to extend interface; keep handle on real object
    4. Create object; add behavior and other protections as needed
  * Implementation and Usage in Spring
    * You'll rarely use a raw Java proxy
    * Leverage AOP
    * `@Transactional` and `@Cacheable` are a couple examples
    * "Spring: Framework in Depth" course has examples of custom AOP
    * Important note: proxies exist in Spring and they add behavior...must understand that layered approach when calling methods within a class

## Operational Patterns in Spring

* Repository Pattern
  * Repository Pattern in Spring
    * Most Spring Data is based on the Repository pattern
    * Can use JDBC to create Repository actions
    * `RepositoryRestResource` adds RESTful web service on top of raw repository
  * Pattern in Brief
    * Not a Gang of Four pattern
    * Introduced in _Domain-Driven Design_ by Eric Evans
    * Simple operations of an entity or business object without knowledge of other entities (as would be the case with DAOs)
    * Under the abstraction, data access operations are hidden
      * With Spring Data, implementation of the logic isn't required as all the access operations are hidden
  * Problem Statement
    * Databases have become the bottleneck
    * Prevent data access logic from leaking into business logic
    * Provide layer of abstraction to hide internals
    * Focus on single business objects at a time
  * Creation Strategy
    1. Start with a Repository interface (using Spring) or create your own interface with generics
    2. Define common data access methods
    3. Apply your business object to a Repository instance
    4. Leverage data access logic under the hood
  * In Action
    * Packages
      * `repository`
    * Tests
      * `SpringDemoApplicationTests.testRepository`
* Template Pattern
  * Template Pattern in Spring
    * Used for remote system calls
    * Provides common behavior that spans across concrete implementations
    * Shares similar behavior across remote calls and template structure
    * Common examples
      * JDBC
      * JMS
      * Rest
  * Pattern in Brief
    * Common operations are hidden in a base class
    * Common flows captured in algorithms with abrstract or default methods for variant code
    * Concrete implementations handle the variant code and extend base class
  * Problem Statement
    * Common code paths often lead to replication
    * Template encourage "don't repeat yourself" (DRY) and reuse
    * Error-prone code can be solved once and reused
    * Templatize common task semantics to reduce costs of implementing new versions
  * Creation Strategy (in Spring, most already exist...it's seldom that you'd need to create your own)
    1. Create base abstract class
    2. Define template method
    3. Implement algorithm, extracting shared code paths into abstract methods
    4. Mark template method as final
    5. Concrete implementations implement abstract methods from base class
  * In Action (paired with MVC)
    * `SpringDemoApplication.restTemplate`
    * `AppController.findPresidentById`
    * `AppController.getPresidentContactById`
* Model-View-Controller (MVC) Pattern
  * MVC Pattern in Spring
    * Core pattern for all Spring web activity
    * Used for RESTful web services
    * Used for traditional HTML pages
  * Pattern in Brief
    * Responsibilities are broken in 3 distinct components
    * Model deliver the data to the view
    * View delivers the experience to the consumer
      * Could be JSON, HTML, XML, etc
    * Controller populates the model
      * Selects the view
      * Merges them together
  * Problem Statement
    * Improves developer efficiencies by separating responsibilities
    * Dynamic and multiple view selection with same controller/model
    * Modify view without modifying the business logic
    * Model returns raw unformatted data for future consumption
  * Creation Strategy
    1. Use engine to engage template
    2. Create view
    3. Create model
    4. Create controller to populate model and return it with the view
  * In Action (paired with Template)
    * `SpringDemoApplication.restTemplate`
    * `AppController.findPresidentById`
    * `AppController.getPresidentContactById`

## Other Framework Patterns in Spring

* Observer Pattern
  * Observer Pattern in Spring
    * `ApplicationListener` interface observes the `ApplicaitonContext` for changes
    * JMS message listener can be looked at as an observer
  * Pattern in Brief
    * An object (subject) maintains list of observers
    * Observer is notified when state change occurs in subject
    * Observer(s) can act on notification if it's applicable or ignore it
  * Problem Statement
    * Reduces coupling in code paths
      * Especially in one-to-many dependency situations
    * Object state changes have downstream effects that need decoupling from main flow
    * Need to dynamically register downstream observers
  * Creation Strategy
    1. Create observer interface
    2. Create subject interface
      1. Register (takes observer object)
      2. Unregister (takes observer object)
      3. Notify function
    3. Implement interfaces in concrete classes as needed
    4. Leverage `Observable`/`Observer` in `java.util`
* Command Pattern
  * Command Pattern in Spring
    * Often in `Runnable` instances
    * `AbstractCommandController` in MVC is an example
  * Pattern in Brief
    * Provides abstraction layer on request-processing scenarios
    * Process calling action gets common interface instead of underlying implementations
    * Allows implementation to be changed without the calling code making changes
    * Provides structure processing layer without coupling
  * Problem Statement
    * Reduces coupling during command processing
    * Added behavior without modifying calling code
    * Allows mulitple implementations of command to exist without modifications to caller
  * Creation Strategy
    1. Create command interface
    2. Include an `execute` and `revert` method
    3. Create command implementations that act on specific objects
    4. Create a calling method that uses the command interface
* Mediator Pattern
  * Mediator Pattern in Spring
    * Discpatcher servlet in Spring MVC is an example
    * Often used when setting state of various components at once
  * Pattern in Brief
    * Provides encapsulation on object(s) behavior
    * Communication channels flow through mediator --> allows mediated classes to work
    * Mediated classed don't communicate with each other, only with mediator
      * This reduces coupling
  * Problem Statement
    * Reduces exponential growth of inter-object communication
    * Reduces tight coupling between interacting objects
    * Makes interaction flows between objects more resistant to change
  * Creation Strategy
    1. Create mediated interface - the things the mediator mediates
    2. Create mediator interface with registration methods for mediated objects and structured commands
    3. Create concrete implementations
    4. In structured commands, mediate behavior to all mediated objects as appropriate
* Interpreter Pattern
  * Interpreter Pattern in Spring
    * Primarily the Spring Expression Language (SpEL) is an example of this
  * Pattern in Brief
    * Use cases are limited, most often found with user input
    * Input is parsed and converted into specific commands or functions
    * Expressions are deemed terminal or non-terminal to determine future parsing
  * Problem Statement
    * A way to interpret user input in variety of formats
    * Prevents ultimate commands from being littered with espression-determining logic
    * Context-aware interpreters can mix and match to call end resulr based on situation
  * Creation Strategy
    1. Create interface for expression
    2. Concrete expressions to evaluation
    3. Terminal and non-terminal implementations of expression interface
    4. Parser that follows expression logic and calls end result

## Next Steps

* Course summary
  * Design patterns and the Gang of Four
  * Major patterns in Spring framework
  * How to implement the most commonly used patterns
  * Discussed why to use patterns
* Suggestions
  * Study the patterns
  * Build kata exercises
    * Simple piece of code to practice
  * Use the patterns while writing code in Spring
  * Reduce complexity in code base using patterns
  * Course only covered a small subset of patterns
  * Study as many as you can
  * Learn the most common ones for your business
  * Leverage kata exercises
