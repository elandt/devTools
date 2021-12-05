# Programming Foundations: Design Patterns

## Design Patterns

* OG Gang of Four Book - Design Pattern: Elements of Reusable Object-Oriented Software Design
  * 23 patterns across 3 categories
    * Creational
    * Structural
    * Behavioral
  * Newer, less dense book - Head First Design Patterns
    * Not by the Gang of Four, but covering the same 23 patterns
      * The course instructors are coauthors of it
* Benefits of Design Patterns
  * Not reinventing the wheel
  * Building resilient code
  * Preparing for future additions
* They're approaches to thinking about software design that incorporates the experience of devs who've had similar propblems
* Design Principles
  * Single responsibility
    * A class should have only one reason to change
  * Open-closed
    * Classes should be open for extension, but closed for modification
  * Loose coupling
    * Strive for loosely coupled designs between objects that interact
      * Loosely coupled - objects that interacted with one another, without knowing a lot about each other
  * Use composition rather than inheritance (or Favor Composition over Inheritance)
    * Classes should achieve code reuse using composition rather than inheritance from a superclass
  * Program to an interface, not an implementation
    * Clients remain unaware of the specific types of objects they use, as long as the objects adhere to the interface clients expect
  * Encapsulate what varies
    * Identify the aspects of your application that vary and seperate them from what stays the same
    * The principle doesn't give any guidance on _how_ to do this...that's where patterns come in
* Principles vs Patterns
  * Principles
    * General Guidelines
  * Patterns
    * Specific solutions

### The Strategy Pattern

* Type: Behavioral
* Definition:
  * This pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable.
  * This lets the algortihm vary independently from clients that use it
* Inheritance can be overused - overuse ca lead to designs that are inflexible and difficult to change
* Example showing the problems/limitations with Inheritance - Designing Classes for Ducks
  * Duck superclass
    * quack()
    * swim()
    * display() - abstract
  * MallardDuck
    * display()
  * RedheadDuck
    * display()
  * RubberDuck
    * display()
    * quack() - overriding the superclass implementation because RubberDucks typically squeak rather than quack
  * Feature Request: Add flying ability to Ducks
    * Leads to adding fly() to superclass
      * Introduces a problem - Rubber Ducks don't fly - need to override fly()
  * DecoyDuck
    * display()
    * quack() - override
    * fly() - override
* Let's try Interfaces instead
  * They define the methods an object _must_ implement in order to be considered a particular type
  * They're an abstract type that specifies a behvaior that classes must implement
  * Allow different classes to share similar behavior
  * Not all classes need to have the same behavior
* Duck example, but with interfaces
  * Interfaces
    * Flyable
      * fly()
    * Quackable
      * quack()
  * Superclass - Duck
    * swim()
    * display() - abstract
  * Contrete/Sub-Classes
    * MallardDuck
      * Implements
        * Flyable
        * Quackable
      * display()
      * fly()
      * quack()
    * RedheadDuck
      * Implements
        * Flyable
        * Quackable
      * display()
      * fly()
      * quack()
    * RubberDuck
      * Implements
        * Quackable
      * display()
      * quack()
    * DecoyDuck
      * display()
  * Shortcomings of this approach
    * Destroys code reuse
    * Changes become a maintenance nightmare
    * Can't easily make runtime changes
* Make use of "Encapsulate what varies" principle
  * For example, altering the flying and quacking code for each new type of duck
  * This principle is common in many design patterns
  * Applying it to our duck simulator
    * quack() varies
    * fly() also varies
    * swim() is pretty standard
* Program to an Interface, not an Implementation
  * Interface is essentially a supertype
  * Applying this to our ducks
    * Interfaces
      * QuackBehavior
        * quack()
      * FlyBehavior
        * fly()
    * Sub-classes
      * QuackBehavior
        * Quack
        * Squeak
        * Mute
      * FlyBehavior
        * FlyWithWings
        * FlyNoWay
    * Duck
      * Properties
        * FlyBehavior flyBehavior
        * QuackBehavior quackBehavior
      * Methods
        * setFlyBehavior()
        * setQuackBehavior()
        * performQuack()
        * performFly()
        * swim()
        * display() - abstract
    * Concrete Classes
      * MallardDuck
        * display()
      * RedheadDuck
        * display()
      * RubberDuck
        * display()
      * DecoyDuck
        * display()
* HAS-A vs IS-A relationships between classes
  * IS-A is an inheritance relationship
    * Ex: A MallardDuck IS-A Duck
  * HAS-A is a relationship of composition
    * Ex: A MallardDuck HAS-A FlyBehavior
    * Compositon allows an object to delegate a behavior to the composed object rather than inheriting the behavior
* Favor Composition over Inheritance
  * Composition _typically_ results in a more flexible design
  * Tying this into the Duck example
    * With the original design (inheritance) ducks were locked into compile time decisions about behavior like how a duck flies or quacks
    * With composition, each duck _has a_ fly behavior, and it can be changed to any of the concrete behaviors that have been implemented

### The Adapter Pattern

* Type: Structural
* Definition:
  * The pattern converts the interface of a class into another interface that clients expect
  * It allows classes to work together that couldn't otherwise because of incompatible interfaces
* General class diagram
  * Client interacts with Target interface
  * Adapter translates Target interface into Adaptee interface
* High level explanation/example
  * Client makes a call to an Adapter
  * Adapter translates the call into the equivalent call on the Adaptee (this may take one or more calls to accomplish)
  * Adaptee returns to Adapter
  * Adapter returns to Client
  * Client is none the wiser that any translation of its original call/request occurred
* Back to the the Duck Simulator example
  * Say we have a Duck interface that has quack() and fly()
  * Testing a MallardDuck in the simulator is easy because it implements the Duck interface, and knows quack() and fly()
  * What if we want to add a Turkey?
    * Turkey uses gobble() instead of quack() to make sound, so you can't simply just call turkey.quack()
    * We create an Adapter
      * TurkeyAdapter implements Duck
      * quack() calls turkey.gobble()
      * fly() calls turkey.fly() (specifically 5 times in the example)
* Adapters use Composition
  * Client is composed with the class with the Target interface
  * Adapter is composed with the Adaptee
  * Adapter delegates calls to the Adaptee, and returns any needed value
  * Client and Adaptee don't know there's an Adapter in the middle
* Adapters can be added easily
  * The Adaptee requires _no_ modification
  * The Client only needs to add the Adapter

### The Observer Pattern

* Type: Behavioral
* Definition:
  * This pattern defines a one-to-many dependency between objects so that when one object changes state, all of it's dependents are notified and updated automatically
    * The "one" is often called the Subject
      * The Subject _owns_ the data
    * The "many" are often called the Dependents, Subscribers, or Observers
* Exemplifies the Loose Coupling design principle
* Real world example: Newspaper publisher
  * You can subscribe to the newspaper, and you'll stay up to date as long as you stay a subscriber
  * Many other people can also subscribe
  * Any subscriber can unsubscribe at any time
* There are multiple ways to approach the class design for this pattern
  * A common one looks like
    * Subject
      * Methods
        * registerObserver()
        * removeObserver()
        * notifyObserver()
      * A ConcreteSubject must implement these methods
    * Observer
      * Methods
        * update()
      * A ConcreteObeserver implements this method
      * update() is called by the ConcreteSubject the ConcreteObserver is subscribed to
  * How does Loose Coupling fit into this
    * Subjects and Observers interact (they're coupled), but they don't know much about each other (loosely)
    * Subject knows only that the Observer implements a specific interface
    * Subject doesn't need to know the concrete cclass of the Observers
    * Subject relies on a list of Observers
    * Observers can be added, removed, or replaced at any time
    * The Subject doesn't care, it just knows to notify the Observers in its list
    * Adding a new Observer is a matter of creating it, and adding it to the Subject's list of observers - no code change to the Subject is necessary
    * Any changes made to the Subject or Observers never impact the other

### The Decorator Pattern

* Type: Structural
* Definition:
  * This pattern attaches additional responsibilities to an object dynamically
  * Decorators provide a flexible alternative to subclassing for extendig functionality
* Example: Coffee Shop
  * Beverage
    * description
    * getDescription()
    * cost()
    * Sub-Classes
      * HouseBlend
        * cost()
      * DarkRoast
        * cost()
      * Decaf
        * cost()
      * Espresso
        * cost()
  * Each coffee IS-A beverage
  * This design gets very cumbersome when you start to look at varients of the beverages, adding soy, whip cream, etc
* Example: Coffee Shop v2
  * Beverage
    * description
    * milk
    * soy
    * mocha
    * whip
    * getDescription()
    * cost()
    * hasMilk()
    * hasSoy()
    * hasMocha()
    * hasWhip()
    * setMilk()
    * setSoy()
    * setMocha()
    * setWhip()
  * Sub-classes
    * HouseBlend
      * cost()
    * DarkRoast
      * cost()
    * Decaf
      * cost()
    * Espresso
      * cost()
  * This still has the downsides of requiring changes for other varients, or cost changes
* The Open-Closed Principle - classes should be open for extension, but closed for modification
  * Open to extensions of behavior
  * Closed to modification - don't touch existing code
  * Goal - be able to easily augement what we bave without modifying existing code
* Inheritance
  * Powerful, but can lead to inflexible designs
  * All classes inherit the same behavior (regardless of whether it's relevant to that class or not)
  * Involves making static compile time desicions
* Composition
  * Can still "inherit" behavior by composing object together
  * Make dynamic, runtime decisions
  * Add new behavior without altering existing code
  * Include behaviors not considered by the creator
* Pattern Class Diagram Overview (what they represent from the coffee shop example)
  * Component (Beverage)
    * methodA()
    * methodB()
  * ConcreteComponent (DarkRoast)
    * methodA()
    * methodB()
  * Decorator (Condiments) - often an Abstract class
    * methodA()
    * methodB()
  * ConcreteDecorator (Whip)
    * Component wrappedObj
    * methodA()
    * methodB()
  * ConcreteDecorator (Mocha)
    * Component wrappedObj
    * methodA()
    * methodB()
* Pattern Class Diagram - Coffee Shop
  * Beverage
    * getDescription()
    * cost()
  * HouseBlend
    * cost()
  * DarkRoast
    * cost()
  * Espresso
    * cost()
  * Decaf
    * cost()
  * CondimentDecorator - often an Abstract class
    * getDescription()
  * Milk
    * Beverage beverage
    * getDescription()
    * cost()
  * Mocha
    * Beverage beverage
    * getDescription()
    * cost()
  * Whip
    * Beverage beverage
    * getDescription()
    * cost()
  * Soy
    * Beverage beverage
    * getDescription()
    * cost()
* With Decorator, we use inheritance to get a common supertype, and we use composition to add decorators

### The Iterator Pattern

* Type: Behavioral
* Definition:
  * This pattern provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation
* Class Diagram Overview
  * Aggregate
    * createIterator()
  * ConcreteAggregate
    * createIterator()
  * Client
  * Iterator
    * hasNext()
    * next()
  * ConcreteIterator
    * hasNext()
    * next()
* Cafe Menu Example
  * Menu
    * createIterator()
  * PancakeHouseMenu
    * ArrayList menuItems
    * createIterator()
  * Diner Menu
    * String[] menuItems
    * createIterator()
  * Cafe
  * Iterator
    * hasNext()
    * next()
  * PancakeHouseIterator
    * hasNext()
    * next()
  * DinerIterator
    * hasNext()
    * next()
* Java has built-in iterators for collection classes, but not for arrays
* Built-in Iterators - these use the Iterator Pattern behind the scenes
  * Java - enhanced for
  * Python - for/in
  * JavaScript - for/of

### The Factory Pattern

* Type: Creational
* Definition:
* Simple Factory Pattern Overview (Pizzas)
  * PizzaStore
    * orderPizza()
  * SimplePizzaFactory
    * createPizza()
  * Pizza
    * prepare()
    * bake()
    * cut()
    * box()
    * Concrete Pizzas
      * CheesePizza
      * VeggiePizza
      * ClamPizza
      * PepperoniPizza
* Simple Factory Pattern (General)
  * Client
  * SimpleFactory
    * createProduct()
  * Product
    * methodA()
    * methodB()
  * ConcretePorduct
    * methodA()
    * methodB()
* Factory Method Pattern
  * Defines an interface for creating an object, but lets subclasses decide which class to instantiate
  * Lets a class defer instantiation to subclasses
* Factory Method Example
  * PizzaStore
    * createPizza()
    * orderPizza()
  * ChicagoStylePizzaStore
    * createPizza()
  * NYStylePizzaStore
    * createPizza()
  * Pizza
    * prepare()
    * bake()
    * cut()
    * box()
    * Concrete Pizzas
      * ChicagoStyleCheesePizza
      * ChicagoStyleVeggiePizza
      * ChicagoStyleClamPizza
      * ChicagoStylePepperoniPizza
      * NYStyleCheesePizza
      * NYStyleVeggiePizza
      * NYStyleClamPizza
      * NYStylePepperoniPizza

## Conclusion

* Patterns aren't a "magic bullet" - they're not a solution to every problem
* Use patterns when there's a current practical need
  * If a problem is only hypothetical, adding the pattern will only add complexity to the system
* Goal should always be simplicity, not how to apply a pattern to a problem
  * Sometimes the best way to keep a design simple and flexible _is_ to use a pattern
