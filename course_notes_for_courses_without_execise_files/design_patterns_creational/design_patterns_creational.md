# Design Patterns: Creational

## Intro

* General definition of design patterns - general solutions to common object-oriented problems
* Creational patterns include
  * Abstract Factory
  * Builder
  * Factory Method
  * Prototype
  * Singleton
* Goal of the course: to create object-oriented software that is more flexible, maintainable, and reliable

## Creational design patterns

* Creational patterns relate to creating or instnatiating objects
  * Provide a way to decouple the client (the part of the system instantiating, and using the objects) from the objects it needs to instantiate
  * Time tested methods for creating new objects
  * Each pattern is a different way to create one or more objects in a flexible, maintainable, and extensible way
    * Address instantiating in different ways to be able to solve different problems
  * Hides complex details of how objects are created
* Behavioral patterns relate to how classes and objects interact
* Structural patterns relate to how classes and object are composed into larger structures
* Design Principles vs Design Patterns
  * Principles: Aimed at the low level of how we put objects together
  * Patterns: Aimed at larger problems

## The Factory Method Pattern

* Encapsulates knowledge about which concrete classes a client uses
  * Effectively promoting, and enabling the concept of code to the interface, and not the concrete/implementation class
* Simple Factory (not one of the "Gang of Four" patterns)
  * Move object creation code from the client code to a simple factory
* Factory Method
  * Involves a super class and sub-classes
  * One sub-class per conctrete object
  * When to use
    * When we can't anticipate the type of object that we will need to create
      * Ex: A pizza shop knows it needs to make pizzas, but not what type of pizzas to make
  * We'd like to use subslassing to offer multiple factories to a client for creating objects
  * The factory subclasses determine which kind of concrete object we'll get
  * Pizza Shop example
    * Client
    * PizzaFactory
      * Methods
        * orderPizza()
        * createPizza()
      * Subclasses
        * NYFactory
          * createPizza()
        * ChicagoFactory
          * createPizza()
    * Pizza
      * Methods
        * preparePizza()
        * bakePizza()
      * Concrete types
        * NYStylePizza
        * ChicagoStylePizza

## The Abstract Factory Pattern

* Similarly to Factory Method, encapsulates knowledge about the concrete classes used, and adds the ability to hide how instances are created and put together where a whole family of products needs to be created
* User Role Abstract Factory example
  * Client
  * User Role (abstract factory)
    * Methods
      * operation()
      * createView()
      * createFunctions()
    * Subclasses
      * Forecaster
        * createView()
        * createFunctions()
      * Observer
        * createView()
        * createFunctions()
  * View
    * Concrete Types
      * ForecasterView
      * ObserverView
  * Functions
    * Concrete Types
      * ForecasterFunctions
      * ObserverFunctions

## The Builder Pattern

* Allows a client to build a complex object in steps, and configure it along the way by abstracting the creation process to an interface
* Differs from Factory pattern because the Factory pattern is concerned with encapsulating the decision about the _type_ of products to create where the Builder pattern is concerned with encapsulating the complexities of how we build an _individual_ object
* Definition: Separate the construction of a complex object from its representation so that the same construction process can create different representations
* When to use
  * create objects of the same kind, for example, a car
  * client has flexibity in how we create the cars (what options the cars have)
  * process of building a car is independent of the parts that make up the car and how they're assembled
* Builder Pattern example (cars)
  * CarDirector
    * Methods
      * construct()
        * ex: Car car = builder.addFrontDoor().addFrontDoor().addMoonRoof().build()
  * CarBuilder
    * Methods
      * addFrontDoor()
      * addBackDoor()
      * addMoonRoof()
    * Concrete Builders
      * SedanBuilder
        * Methods
          * addFrontDoor()
          * addBackDoor()
          * addMoonRoof()
          * build()
      * SUVBuilder
        * Methods
          * addFrontDoor()
          * addBackDoor()
          * addMoonRoof()
          * build()
  * Car
    * Sedan
    * SUV

## The Prototype Pattern

* Copies and existing object and then modifies the new one
* Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype
* Why use this pattern
  * when we copy an existing object, we get the complex setup for free
  * don't need to know the concrete class of the object we're creating (the object copies itself)
  * client is independent of how an object is created
* Instance type can be determined at runtime by system state
* Java's Cloneable interface allows objects to clone themselves with the clone() method
* Prototype Pattern example
  * Client
    * Methods
      * operation()
        * ex: Prototype p = prototype.clone()
  * Prototype
    * Methods
      * clone()
    * Concrete Types
      * ConcreatePrototype1
        * Methods
          * clone()
            * return a copy of this;
      * ConcreatePrototype2
        * Methods
          * clone()
            * return a copy of this;

## The Singleton Pattern

* Creates one and only one instance ever to allow access control to a resource, ex. network connection, or db connection
* Definition: Ensure a class only has one instance, and provide a global point of access to it
* When to use:
  * need to ensure only one instane of a class exists
  * instance must be easily accessible to clients
* Caveat
  * Implementation of Singleton is language dependent, and can be tricky to get right
    * think about multi-threading and ensuring only one instance exists for all threads
* Singleton Pattern example
  * Singleton
    * static uniqueInstance
      * uniqueInstance = new Singleton();
    * static getInstance()
      * return uniqueInstance;
    * private Singleton()
* Encapsulates the code that is managing a resource
* The Singleton's sole responsibility is managing a resource
* Singleton pattern is _not_ loosely coupled
  * A change to the singleton can impact the rest of your code
