# Course Notes

## Deviations from the Instructor

* Why deviate
  * Course was released in 2019, and is being completed by me in 2023
    * I am going to be using newer versions of technologies used by the instructor
  * I don't want to install some of the technologies on my actual machine (looking at you Node)
* Deviations
  * Java - version 17
  * Node - 18.18.0
  * Spring Boot - 3.1.4
  * Using Gradle instead of Maven
  * Using a devcontainer to avoid installing course specific tech on my machine
  * Not creating `ReactAndSpringApplication.java` because it's not necessary
    * Using the `CrmApplication.java` that Spring Initializr created
  * Putting the data access layer in its own package (`repo`)
  * Putting `DemoLoader.java` in its own package (`startup`)
    * Could also simply include this in `CrmApplication.java` and just add the `ContactRepository` dependency and override `run()` right in the base application class

## What you Need/Need to Know

* Language Experience
  * Java
  * JavaScript, specifically ES6 syntax
* Terminal - experience will help, but not a must

## Overview of the full-stack and MVC

* MVC
  * Model --> data
  * View --> visuals (HTML)
  * Controller --> logic
* MVC with React and Spring
  * Model --> Spring
  * View --> React
  * Controller --> Spring/React
