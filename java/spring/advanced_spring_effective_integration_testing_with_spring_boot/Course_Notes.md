# Course Notes

## What you Need/Need to Know

* Prereq Knowledge
  * Java
  * Spring Framework and Spring Boot
  * Functional programming
    * Some exposure to the Streams and Lambdas introduced in Java 8
  * Experience with JUnit
* Tech
  * Java
    * Course: 15
    * Me: 17
  * Spring Boot
    * Course: 2.4.1
    * Me: 3.4.1
  * Any modern IDE
    * Course: Intellij

## Why bother testing

* Unit tests target a small unit of code (class or method)
* Unit tests remove dependencies by using a test implementation or mocking them
* Interation tests can be
  * Tests that cover multiple units
  * Tests that cover multiple layers, e.g., controller --> service, service --> data/repository, etc layers tested together
  * Tests that cover the full path, e.g., controller --> service --> data/repository
  * Tests that cover interactions with outside systems/dependencies, e.g., databases, SFTP servers, other API endpoints
* When expanding outside the unit test scope, we can use `@SpringBootTest` to start and autowire the full `ApplicationContext`

## Power of Spring Boot Libraries

* Code session - creating an app and exploring Spring Boot testing libraries
* Name: `student-services`
* Dependencies
  * Lombok
  * Spring Web
  * Spring Data JPA
  * H2
  * Contract Verifier
* Testing Libraries included in Spring Boot
  * JUnit
    * `@Test`
    * `@BeforeAll`
    * `@Nested`
    * `@DisplayName`
  * SpringBootTest
    * `@SpringBottTest`
    * `@DataJpaTest`
    * `@WebMvcTest`
    * `@MockBean`
  * Mockito
    * `@Mock`
    * `@InjectMock`
      * Called out in course, but I won't use it because it doesn't necessarily enforce expected behavior
      * In my experience using constructor injection yields more expected results when testing with mocks
    * `when.(...).then(...)`
    * `given(...)`
  * AssertJ
    * `assertThat(...).hasSize(...).contains(...).doesNotContain(...)`
    * `assertThat(...).startsWith(...).endsWith(...)`
  * JSONAssert
    * `assertEquals("{id: 123}", actual, ...);`
  * JSONPath
    * `$.store.book[*].author`
    * `$.store.book[0].title`
* Test code tends to be disorganized
  * The given-when-then pattern can help
    * BDD: behavior-driven development
    * BDD encourages a more human-friendly style of writing tests by organizing the code into three parts
      * given: preconditions and requirements for our action
      * when: action that we want to test - typically only calling one or two lines of code
      * then: verification of what should happen after execution of the action
    * Promotes clarity through a more natural language flow in the test
    * BDDMockito vs Mockito
      * Mockito: `when(methodCall).then(doSomething)`
      * BDDMockito: `given(methodCall).will(doSomething)`
    * AssertJ vs JUnit
      * JUnit - easy to mix up which arg is the expected result and which is the actual result
        * `assertEquals(student.getName(), "Mark");`
        * `assertEquals("Mark", student.getName());`
      * AssertJ - can be more clear regarding what the actual and expected values are
        * `assertThat(student.getName()).as("Check that Name is set").isEqualTo("Mark");`
        * Rich set of assertions
        * Helpful error messages
        * Improves code readability
        * Easy to use
        * Descriptive assertions
          * `assertThat(user.getAge()).as("%s's age should be 100", user.getName()).isEqualTo(100);`
        * Comparing objects
          * `assertThat(object).isEqualToComparingFieldByField(anotherObject);`
        * Iterable assertions
          * `assertThat(list).isNotEmpty().doesNotContainNull().containsSequence("2", "3");`
        * Exception assertions
          * `assertThatThrownBy(() -> { throw new Exception("boom!"); }).isInstanceOf(Exception.class).hasMessageContaining("boom");`
        * AssertJ also has BDD aliases
          * AssertJ: `assertThat(person.getAge()).isEqualTo(40);`
          * BDDAssertions: `then(person.getAge()).isEqualTo(40);`
    * BDDMockito + AssertJ
      * Powerful feature set
      * Reduce boilerplate code
      * Write more readable verification code blocks

## Intgration testing against a real DB

* Effective ways to test data access
  * Tests for DBs
    * What to mock?
    * Risks with custom queries
    * Risks with in-memory dbs
    * Avoid pitfalls
  * Three keys to effective integration testing of the db layer
    * Choose the right interactions to test
      * Example

      ```java
      class Student {
        @Id @GenerateValue // Validated on startup
        private Long id;
        private String name;
      }

      interface StudentRepository extends CrudRepository<Student, Long> {

        // Query method
        Student findByNameBla(String name);  // Validated on startup - "Bla" is invalid and startup would fail

        // JPQL (Java Persistance Query Language) query
        @Query(value = "Select s from StudentsTypo s")  // Validated on startup - "Typo" is invalid and startup would fail
        List<Student> findAllStudentsCustom();

        // Native SQL
        @Query(value = "Select * from StudentsTypo", nativeQuery = true) // NOT validated on startup - should target with test
        List<Student> findAllStudentsCustomNative();
      }
      ```

    * Make tests fast by using test slices
      * `@SpringBootTest` loads the full `ApplicationContext` which can mean a bunch of unnecessary beans
      * Spring Boot offers various test slices for more targeted integration testing
        * Data: `@DataJdbcTest`, `@DataJpaTest`, `@DataLdapTest`, `@DataMongoTest`, `@DataNeo4jTest`, `@DataR2dbcTest`, `@DataRedisTest`
        * Web: `@RestClientTest`, `@WebServiceClientTest`, `@WebFluxTest`, `@WebMvcTest`
        * Serialization: `@JsonTest`
    * In-memory database != production database
      * Embedded db
        * H2/HSQL
        * Compatibility mode (MySQL and Postgres)
        * Risk: not in sync with production db
      * Testcontainers
        * `@Testcontainers`
        * Throwaway db instances in Docker
        * Simple setup
        * Define database type and version
        * Each time there's a fresh instance of the db
          * Can be costly, so may need to have a single instance for multiple tests and manage clean-up after one or more tests
* Writing integration tests for a JPA Repository
  * Testing approaches
    * Bottom-up/inside-out
      * Test data layer, then service, then endpoints
      * Typically used when there are not many concerns with the integration points
    * Top-down/outside-in
      * Test endpoints, then service, then data layer
      * More common when the integration points need to take priority
  * Course is going to use
    * Bottom-up approach and test the respository first
    * TDD/Red-Green-Refactor
      1. Write failing test
      2. Write just enough code to pass the test
      3. Improve the code without changing its behavior
* Challenge
  * Create a custom query to fetch average grade for active students
  * Extend `student` with `boolean active` and `int grade`
    * `grade` is anything between 0 - 100
  * How to implement
    1. Create students
    2. Create failing test and verify the average grade
    3. Write implementation for the query

## Integration testing against services with caching support

* Why have a service layer
  * Separation of concern
  * Loose coupling
  * Orchestration
  * Caching
* Coding Session - fetching a student from the service layer using the data layer
* Testing caches
  * Need to verify that the db isn't hit every time
* Mocking in integration tests
  * With `@SpringBootTest` (depending on version of Spring Boot) you can use
    * Before v3.4.0: `@MockBean` and `@SpyBean` (now marked as deprecated and for removal in v3.6.0)
    * Starting with v3.4.0: `@MockitoBean` and `@MockitoSpyBean`
* Challenge
  * Write an integration test for exceptions
    * Write an integration test for a custom exception
      * Thrown by `StudentService.findStudentById`
      * Rather than returning `null` when not found, throw `StudentNotFoundException`
    * How to implement
      1. Write a failing test
      2. Implement an exception
      3. Refactor so test passes

## Integration testing against a web controller

* Testing Controllers
  * Call business logic?
  * Endpoint exposed?
  * Input validated?
  * Exceptions converted?
* An example controller may include
  * URL mappings
  * Deserializing input
  * Validating input
  * Business logic or calls to a abstracted business logic
  * Serializing output
  * Translating exceptions
* Unit testing would cover
  * Testing the controller in isolation
  * Mocking any interactions with dependencies
  * Calling controller methods and verifying the responses
  * Because Spring isn't loading anything or handling the annotations, we're dealing with a plain Java class rather than a controller
  * Of the 6 items listed above, unit testing would only cover the business logic, if present
* Integration tests allow all 6 items to be covered by tests
  * Can use `@WebMvcTest` or `@WebFluxTest` test slices
    * `@WebMvcTest`
      * Auto configures `MockMvc`
      * No need for HTTP server
      * Not covered: `@Service`, `@Component`, `@Repository`
      * Can use the following to mock collaborators/dependencies (depending on version of Spring Boot)
        * Before v3.4.0: `@MockBean` and `@SpyBean` (now marked as deprecated and for removal in v3.6.0)
        * Starting with v3.4.0: `@MockitoBean` and `@MockitoSpyBean`
    * `@WebFluxTest`
      * Test Slice for reactive applications
      * Auto configures `WebTestClient`
      * No need for HTTP server
  * `@SpringBootTest` also is a good tool here
    * It loads the full application
    * Mocked or real server
      * By default, it loads a Mock Web environment
      * Can be configured to run on a production environment
    * Uses real dependencies
      * Can be mocked
    * Cons: slow running tests
      * Remember, it's loading the whole app
* Coding Session - make a call to student-service and assert certain things about the shape of the response
* Coding Session - handling exceptions for when no student exists for the given ID

## Integration testing for the client app

* Integration testing without making an external API call
  * Mock out calling the service
    * `RestTemplate` testing
      * Mock out `RestTemplate` to simulate the external API interactions
        * Risks
          * URL typos
          * Path variable typos
          * Message conversion logic
      * `@RestClientTest` test slice
        * This is a better option than mocking the `RestTemplate`
        * Comes with a preconfigured `MockRestServiceServer`
          * Removes need for an actual server
          * Limited to only `RestTemplate`
    * `WebClient` is even harder to test than `RestTemplate`
      * This is due to the fluent API and objects pass between the calling code and the mock
        * This requires providing a different mock object for each call in the chain
          * Verbose and cumbersome
          * Requires knowing implementation details of how our service uses `WebClient`
            * This makes the tests brittle
      * Recommendations from the Spring team
        * `OkHttp`
        * `MockWebServer`
        * `WireMock`
      * `WebClient` testing
        * You can still set up expectations like with `MockRestServiceServe`
          * Key difference: rather than having some predetermined response(s), you keep the HTTP client underneath, leading to more complete testing
        * Over an actual socket, it's more natural to simulate and test the impact of
          * Slow network conditions
          * Chunked responses
* Writing integration tests for rest endpoints
  * Coding Session - creating a client app (`student-client`) to use for testing
    * After this video, there is a bug with the fields on the `Student` objects within the `student-service` and `student-client`
* Introduction to Spring Cloud Contract
  * At this point, both apps have green tests, but there's no real integration between the two apps
  * How can we ensure the contract between consumer and producer API works?
    * Deploy all microservices and run end-to-end tests
      * Pros
        * Closest to production
      * Cons
        * High price for dedicated infrastructure
        * Tests can be slow and flaky
    * Deploy mocks
      * Pros
        * Fast feedback
        * No infrastructure requirements
      * Cons
        * Potential false assurance that everything is fine
    * Spring Cloud Contract
      * Fast feedback
      * No need to set up all the microservices in the environment/ecosystem
      * Allows for the producer to create reusable stubs that can be stored in a central repository, like Artifactory, and can then be used by consuming systems
      * Consumer uses a stub runner
      * Outcome?
        * Consumers fail fast if not in sync with producer
        * Producers can see if changes break existing contracts with clients or not
        * Approach is called "consumer-driven contract testing"
* Ensuring client app (rest call) and web app (controller) are in sync
  * Course calls out that Spring scans `src/test/resources/contracts` for contract...but Spring Initializr `src/contractTest/resources/contracts` when `spring-cloud-starter-contract-verifier` included as a dependency
    * Per documentation for setting up Spring Cloud Contract Verifier with a gradle project
      * Spring Cloud Contract Verifier looks for stubs in the `src/contractTest/resources/contracts` directory
      * For transitional purposes the plugin will also look for contracts in `src/test/resources/contracts`
        * This directory is deprecated as of Spring Cloud Contract 3.0.0
      * Source: [Add stubs](https://docs.spring.io/spring-cloud-contract/docs/current/reference/htmlsingle/#gradle-add-stubs)
    * In order to use `stubsMode = StubRunnerProperties.StubsMode.LOCAL` with `StubRunner`
      * Add the following snippet to the `build.gradle` of the producing application

      ```groovy
      publishing {
          publications {
              stubs(MavenPublication) {
                  artifact verifierStubsJar
              }
          }
      }
      ```
      * You'll then need to execute the `publishStubsPublicationToMavenLocal` gradle task
        * You can do this manually or automate it by having it finalize another task, for example the `contractTest` task

        ```groovy
        contractTest.finalizedBy publishStubsPublicationToMavenLocal
        ```
