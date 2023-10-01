# Course Notes

## What you Need/Need to Know

* Java
  * JDK 17 used in the course
  * Intermediate understanding of Java
* Spring
  * Base knowledge of Spring dependency injection
* Java Persistence API (JPA)
  * Base understanding
* Maven
  * Used by instructor
  * I'm using Gradle
* IDE

## Spring Data

* Umbrella of projects
* Commonality of data store access between projects
* Special traits projects for various data stores
* Spring Data Commons abstracts away from any particular data store
  * Depended on by the more specific Spring Data projects
  * Java business entities <--> Persistent target data store records
  * Create records
  * Lookup records
  * Update records
  * Delete records
  * Repository Pattern
    * Reposiporty Java interfaces
    * Create, Read, Update, and Delete: CRUDRepository interface
    * JpaRepository
    * MongoRepository
    * GemfireRepository

## JPA

### Obect-Relational Mapping (ORM)

* Physical model to logical model
* Physical = relational db
* Logical = Java domain objects
* Possible to do in vanilla Java, but nasty

### Example: University

* JPA Maven project with H2 in-mem db
* JPA entities found in `model` package
  * Staff
  * Student
  * Department
  * Course
* EntityManager Clients found in `dao` package
  * StaffDao
  * StudentDao
  * DepartmentDao
  * CourseDao
* Business Services found in `business` package
  * UnversityService
  * DynamicQueryService
* Application-Managed persistence in JUnit Tests
  * `SimpleDBCrudTest.java` - invokes db create, read, update, and delete
  * `FindByOneAttribute.java` - invokes simple queries that look up entities by a single attribute
  * `FindByClauseAndExpressions.java` - invokes complex queries
  * `PagingTest.java` - invokes queries that employ paging and sorting
  * `CriteriaQueryTest.java` - invokes dynamic queries
  * `UnversityFactory.java` - helper to set up seed data before running a test
* A couple chapters of this course are going to take this example, and refactor it to use Spring Data JPA
