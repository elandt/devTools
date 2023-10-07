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
* A couple chapters of this course are going to take this example, and refactor it to use Spring Data JPA and Spring Boot

### Why Spring Boot and Spring Data JPA

* Dependencies - Spring Boot starter model is easy to manage
* Spring Data JPA automatically configures the persistence context

### Custom Queries in Spring Data JPA

* Spring uses Bean Utils and reflection to implement methods defined in Repository interfaces
  * This requires conventions/rules to be followed for the query to be produced as intended
    * [Appendix C: Repository query keywords](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)
    * [Appendix D: Repository query return types](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types)
    * Similar resources are available for other Spring Data modules
      * [Mongo: Repository query keywords](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repository-query-keywords)
      * [Mongo: Repository query return types](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repository-query-return-types)
  * Rules (some examples - see above links for additional details for Spring Data JPA)
    * `findBy`
      * Single-Return type: `Class` or `Optional<Class>`
      * Multi-Return type: Collection container (i.e. List, Iterable, Collection)
      * Starts with `findBy` keyword
      * Entity attribute name (use camel case)
      * Optionally, chain sub-attribute names
      * Parameter(s) with datatype of the entity attribute
    * `countBy`
      * Return type `long`
      * Starts with `countBy` keyword
      * Entity attribute name (use camel case)
      * Optionally, chain sub-attribute names
      * Parameter(s) with datatype of the entity attribute
* Verifed at BootStrap
  * Fast failure
  * Verified during Spring Application Bootstrap phase
    * `CourseRepository` invalid syntax example:
      * `Optional<Course> findByTitle(String courseName);`
      * `QueryCreationException: No property title found for type Course`
* Queries with Logical Expressions
  * `List<Student> findByFullTimeOrAge(boolean isFullTime, int targetAge);`
  * `List<Student> findByAttendeeFirstNameAndAttendeeLastName(String firstName, String lastName);`
* Expressions with Operators
  * `List<Student> findByAgeGreaterThan(int minimumAge);`
  * `List<Student> findByFullTimeOrAgeLessThan(boolean isFullTime, int maxAge);`
  * `List<Student> findByAttendeeLastNameIgnoreCase(String lastName);`
* Expressions Limiting and Ordering
  * Find the student with the last name that comes first alphabetically
    * `Student findFirstByOrderByAttendeeLastNameAsc();`
  * Find the oldest Student
    * `Student findTopByOrderByAgeDesc();`
  * Find 3 oldest Students
    * `List<Student> findTop3ByOrderByAgeDesc();`
  * `First<number>` and `Top<number>` are interchangeable
* `@Query`-annotated methods
  * Examples

    ```lang=java
    @Query("JPQL Query String")
    ReturnValue anyMethodName(zero or more parameters);

    @Query(value="SQL Query String", nativeQuery=true)
    ReturnValue anyMethodName(zero or more parameters);
    ```

  * Native queries are **_not_** verified at bootstrap
  * Can allow for cleaner method names
  * Can be used for complex JPQL queries
    * As of course recording (Released 1/23/23) JPQL doesn't allow limiting the result set
  * Sometimes using the native query language is necessary
* Paging and Sorting
  * JpaRespository already extends the PagingAndSortingRepository
  * Sorting can be accomplished by passing a `Sort` to a query method, or by defining the sort using property expressions
    * `findAllByOrderByMemberLastNameAscMemberFirstNameAsc` is equivalent to `findAll(Sort.by("member.lastName", "member.firstName"))`
  * Paging
    * `PageRequest.of(<page number>, <size of page>, <optional Sort>)`
    * `Sort nameSort = Sort.by("member.lastName", "member.firstName")`
    * `Pageable firstPageOfFive = PageRequest.of(0, 5, nameSort)`
    * `Pageable secondPageOfFive = PageRequest.of(1, 5, nameSort)`
* Dynamic Querying
  * `JpaSpecificationExecutor` can be used to create dynamic queries
  * Querysdl is another means of creating dynamic queries
    * Querydsl Predicates != JPA Predicates
    * Requires additional project dependencies
      * From digging around online
        * The Querydsl project seems to potentially be dead
        * Most of the guidance I found references the Maven setup
        * I tried a few Gradle examples that I found, but to no avail (Q-classes wouldn't generate)
          * Frankly, I don't care enough about using Querydsl to actually figure it out for Spring Boot 3.x and Gradle
          * Going to include the examples from the course, but they'll be commented out
  * QueryByExample (QBE)
    * Uses
      * User-friendly alternative to SQL
      * Lookup objects similar to another object
      * Independent of underlying data store
      * Frequently refactored code
    * QBE has difficulty querying:
      * Nested properties contraints
      * Complex string matching
    * Requires extending `QueryByExampleExecutor<T>`
      * `JpaRepository` already extends this
    * An `Example<T>` can be created using a `Probe` or `Probe` + `ExampleMatcher`
      * Using an `ExampleMatcher` allows for refining the query criteria
        * `ExampleMatcher` follows the builder pattern so you can add as many restrictions as needed
* Spring Data REST - `spring-boot-starter-data-rest`
  * Exposes Spring Data repositories as REST API endpoints
    1. Finds the Spring Data repositories
    2. Creates an endpoint that matches the entity name
    3. Appends an 's' (configurable if default not desired)
        * This is done using a `@RepositoryRestResource` annotation
    4. Exposes methods as a RESTful resource API over HTTP
  * Example method to endpoint mappings

  |Query Method/Code Snippet|REST API Endpoint|
  |-------------------------|-----------------|
  |`departmentRepo.findAll();`|GET /departments|
  |`departmentRepo.findById(5);`|GET /departments/5|
  |`departmentRepo.findByName("Science");`|GET /departments/search/findByName?name=Science|
  |`staffRepo.findAll()PageRequest.of(1,5);`|GET /staffs?page=1&size=5|
  |`staffRepo.findByLastName("jones", PageRequest.of(0,3));`|GET /staffs/search/findByLastName?lastName=jones&page=0&size=3|
  |`staffRepo.save(new Staff(new Person("Jane","Doe")));`|POST /staffs {"member": {"firstName": "Jane", "lastName": "Doe"}}|
  |`var two = staffRepo.findById(2); two.setMember(new Person("John","Smith")); staffRepo.save(two);`|PUT /staffs/2 {"member": {"firstName": "John", "lastName": "Smith"}}|
  |`var two = staffRepo.findById(2); two.getMember().setLastName("Smith"); staffRepo.save(two);`|PATCH /staffs/2 {"member": {"lastName": "Smith"}}|
  |`var one = departmentRepo.findById(1); departmentRepo.delete(one);`|DELETE /department/1|

* Responses will return Hypermedia enabled JSON content
  * Relationships to child entities are links even if the fetch type is set to `EAGER`
    * This behavior can be overriden using Projections

* Reactive Querying
  * Query Methods must return a `Mono` or `Flux` otherwise a runtime exception will occur when invoked
    * Each publisher (`Mono` or `Flux`) requires a subscriber
      * The subscriber is what actually triggers the interaction
    * `Mono` - at most 1 result
      * Use `.block()` to interact with the datasource within the same thread as the method call
    * `Flux` - 0 to N results
      * Use `.subscribe()` to interact with the datasource within the same thread as the method call
  * WebFlux in the web tier automatically subscribes
  * **Spring encourages using the reactive stack for new projects that use an asynchronous data store**

## What's Next

* Creating Your First Spring Boot Microservice
* Introduction to Docker for Java Developers
