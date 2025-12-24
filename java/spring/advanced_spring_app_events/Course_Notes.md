# Course Notes

The application events provided by Spring can be used to implement flexible and loosely coupled architectual design

## What you need to know

* Java - course videos use Java 15
* Spring Framework
* Spring Boot - course videos use Spring Boot 2.5.x
* Any Java IDE

## Exploring the Power of Spring Events

* Why Spring Events vs Direct Method Calls
  * Event System
    * Often overlooked
    * Provide a way for components to communicate with more flexibility than traditional method calls
    * Flexible and loosely coupled architectural design
      * Coupling - degree of knowledge that one object has about the other object
      * Loose Coupling
        * Reduce the dependency between multiple components
        * Reduces the risk of unanticipated and unintentional impacts of changes in one component on the other component(s)
        * Simplifies testing, maintenance, and troubleshooting problems
    * Provides pub/sub capabilities
      * Publishers and event subscribers (listeners) are not tied up - can be used independently of each other
      * Allows sending data to other components effectively
      * Changes in the publisher or listener will not affect each other
        * Unless there's a contract change
      * Allows invoking logic on multiple components at the same time
      * Fits with/basically is the observer design pattern
        * Pattern term --> Event System term
          * Observers --> Listeners/Subscribers
          * Subject --> Publisher
  * Traditional Method Calls
    * Tightly coupled
      * A change to one of them often requires changes in others
  * Open-Closed Principle
    * Open for extension, but closed for modification
  * Event-driven architectures allow new functionalities to be added with no disruption of the old ones as the domain expands
* Highlights of Spring Events
  * One of the core capabilities provided by Spring Framework
  * Core components of Spring Events
    * Events
      * Simple POJO containing data
    * Publishers
      * Constructs and publishes event objects
      * Uses `ApplicationEventPublisher` bean, and the `publishEvent(someEvent)` method of it
    * Listeners
      * Multiple flavors
        * Can use annotations - `@EventListener`
        * Can implement the `ApplicationListener<T>` interface
      * All listeners are automatically registered by Spring
  * Synchronous by default
    * Publisher thread blocks until all listeners finished processing the event
  * Async is supported
    * Event is published in a new thread
    * Publisher thread released independently of the listener
  * Transaction-bound events
    * Allows binding an Event Listener to a phase of the current transactions
      * Before/After Commit
      * After Rollback
      * After Completion
    * More flexibility when the outcome of the current transaction matters to the listener
  * Filtering Events
    * Listeners can use filters to only process events of their given type that also meet particular criteria
  * Predefined Events
    * Application
      * `ApplicationStartingEvent`
      * `ApplicationEnvironmentPreparedEvent`
      * `ApplicationContextInitializedEvent`
      * `ApplicationPreparedEvent`
      * `ApplicationStartedEvent`
    * Context
      * `ContextRefreshedEvent`
      * `ContextStartedEvent`
      * `ContextStoppedEvent`
      * `ContextClosedEvent`
  * Listeners before Context is Created
    * Listeners that need to listen for events that occur before the Application Context is created need to be registered manually
      * Relying on the automatic registration would mean that the listener would be registered after the events it's meant to listen for have been published and therefore be missed

## Implementing Custom Spring Events

* Creating and Publishing Custom Events
  * Demo App
    * An ecommerce platform with Orders, Products, and Customers
  * This lesson will focus on customer registration
    * Some operations that might be triggered by the customer registration
      * Confirm Email or Welcome Email
      * Registration in a CRM system
      * Introductory promotional offer
    * Starting state
      * `CustomerService.java` only calls
        * The `save()` method of the `CustomerRepository.java`
        * The `sendRegisterEmail()` method of the `EmailService.java`
    * Adding operations like adding a promotion, external calls, crm registration using traditional method calls:
      * Could create cyclic dependencies
      * Violates the single responsibility principle
      * Increases testing difficulty
    * Using the event system is a way to solve this
      * Need to:
        * Create a POJO for the event
          * Can extend `ApplicationEvent`, but not required as of Spring Framework 4.2
        * Add the `ApplicationEventPublisher` dependency to `CustomerService`
        * Call the `publish` method of the `ApplicationEventPublisher` passing in a new `CustomerRegisteredEvent` containing the saved customer
* Adding event listener
  * Any event can have multiple event listeners doing different work
  * Again, multiple ways to define a listener
    * Can use annotations - `@EventListener`
      * Introduced in Spring Framework 4.2
      * Event type of the listener is based on the method signature
        * `someMethod(Foo.class)` would only listen for events of type `Foo`
        * `@EventListener({Foo.class, Bar.class}) anotherMethod()` would listen for events with types `Foo` or `Bar`
        * `yetAnotherMethod()` would listend for events of all types
      * Can have a non-`void` return type
        * When return type is non-`void`, the result is sent as a new event
          * If the return type is a `Collection` or an array, each element is sent as its own event
      * Can also control order of event listeners for the same type of event using `@Order(#)`
    * Can implement the `ApplicationListener<T>` interface
      * With this approach
        * Spring uses the signature of the listener to determine if it matches the event
        * Limitations:
          * It can only be used to listen for objects that extend `ApplicationEvent`
          * Can only listen for one event type
          * Can only have a `void` return type
* Challenge
  * Create `CustomerRemovedEvent`
  * Create a listener that captures the new event, and sends the appropriate email
  * Use existing method(s) from the `EmailService`
  * Run `removeCustomer_forExistingCustomer_sendsAnEmail` test to confirm expected behavior

## Async and Filtering Events

* Implementing async events
  * Enable Async processing by
    * Adding the `@Async` annotation to the event listener
    * Enabling async within the application by applying the `@EnableAsync` to the configuration, i.e. the application class
  * `@Async` Limitations
    * Cannot publish event by returning a value
      * Can still publish a subsequent event, but need to do it manually with a call to the `publish` method of an `ApplicationEventPublisher` dependency
    * Exceptions are not propagated to the caller
      * Can implement `AsyncUncaughtExceptionHandler` interface to process any asynchronus exceptions
* Filtering events
  * `@EventListener` has a `condition` attribute that can be used to filter events
    * It uses SpEL to define the condition
    * The condition is met if the condition is truthy
      * Evaluates to one of
        * `true`
        * `"true"`
        * `"on"`
        * `"yes"`
        * `"1"`
    * Default `condition` is `""`, i.e. events of the appropriate type are always handled
    * Some example conditions
      * `#event` - references the event
      * `#event.customerType eq 'b2c'`
      * `@myBean.test(#event)` - uses another bean to handle the filter logic

## Transactional Events

* Transaction Bound Events
  * Why Transaction-bound Events
    * Useful when the outcome of a transaction matter
      * For example, sending an email to a customer about the state of an order
        * If the event listener for sending the email is not aware of the outcome of the transaction, the email could be sent, but the state change never occurred in the system due to an error in the transaction and a rollback.
    * Reminder `@Transactional` can be applied to a class or a method
      * It groups multiple database writes into a single atomic operation, i.e. all writes occur, or none do
  * `@TransactionalEventListener` is used for transaction-bound event
    * Does not make the event listener transactional itself, only enables delaying event consumption until a certain transactional outcome
    * Enables fine-grained control over when events are processed
    * Four phases available
      * `BEFORE_COMMIT`
      * `AFTER_COMMIT` - default - handled only after the transaction successfully commits
      * `AFTER_ROLLBACK` - handled only after a rollback has occurred
      * `AFTER_COMPLETION` - handled after the transaction completes regardless of whether the transaction commits or is rolled back - cases where the listener should _always_ run
  * Avoid infrastructure interactions within `@EventListener` that is part of a transactional context
* Implement custom tranasaction event
  * Example will solve the potential issue that could occur if an order state email is sent, but the transaction to change the order rolled back
* Challenge: Create a Custom TransactionalEventListner
  * Call `TicketService.createTicket(...)`
  * Verify with `TicketServiceTest`

## Next Steps

* [Software Architecture: Domain-Driven Design by Allen Holub](https://www.linkedin.com/learning/software-architecture-domain-driven-design)
* [Spring: Effective IntegrationTesting with Spring Boot by Terezija Semenski](https://www.linkedin.com/learning/advanced-spring-effective-integration-testing-with-spring-boot)
