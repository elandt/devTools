# Course Notes

## Adding Query Methods to a CrudRepository

* Spring handles implementation through introspection and reflection as long as rules are followed for declaring methods, and correctly map entity properties to the method signature
* Simple Query Method Signature
  * Return type
  * Method signature format
    * start with `findBy`
    * Entity attribute name using camelCase
      * Can be chained to span entities
  * Parameters with datatype of the entity attribute
  * Can span multiple entities
  * Basic examples for the entities in the `explorecali` project
    * `List<Tour> findByTourPackageCode(String code);`
    * `List<Tour> findByPrice(Integer price);`
    * `Optional<Tour> findByTitle(String title);` - would return one tour because titles are unique
    * `Collection<Tour> findByDifficulty(Difficulty difficulty);`
    * `Optional<Tour> findByRegion(Region region);` - Invalid because it throws IncorrectResultSizeDataAccessException
      * `List<Tour> findByRegion(Region region);` - A valid way to accomplish the above
  * More complex examples for the entities in the `explorecali` project
    * `List<Tour> findByTourPackageCodeAndRegion(String code, Region region);`
    * `List<Tour> findByRegionIn(List<Region> regions);`
    * `List<Tour> findByPriceLessThan(Integer maxPrice);`
    * `List<Tour> findByKeywordsContains(String keyword);`
    * `List<Tour> findByTourPackageCodeAndBulletsLike(String code, String searchString);`
    * `List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceLessThan(String code, Difficulty difficulty, Region region, Integer maxPrice);`
    * Check Spring Data JPA spec for all filter combinations
  * Advanced Examples
    * Using JQL with the `@Query` annotation
    ```java
    @Query("Select t from Tour t where t.tourPackage.code = ?1 and t.difficulty = ?2. and t.region = ?3 and t.price <- ?4")
    List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxprice);
    ```
    * The above is equivalent to `List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceLessThan(String code, Difficulty difficulty, Region region, Integer maxPrice);`

## RESTful APIs

* An API is not truly RESTful unless follows a uniform interface (Roy Fielding - [Dissertation](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm))
  * One constraint of a uniform interface is when "hypermedia as an engine of application state (HATEOAS)" is employed
    * Should expose documentation and automatically provide navigation between resources
  * Spring Data REST APIs are hypermedia driven ootb
    * Uses hypermedia application language (HAL) standard for associating rousource objects to each other
    * For explorecali, no coding is necessary to create the CRUD endpoints
      * Spring Data REST provides a variety of endpoints based on the configuration we already have (our CRUD Repositories)
        * Create
          * Post
        * Read
          * Get
        * Update
          * Put/Patch
        * Delete
          * Delete
        * Search
          * May need to annotate search parameters with `@Param("name-for-param-in-url")`
            * Didn't appear to be necessary in course videos, but without it, my code threw and error
              * Only found one mention of `@Param` in [Spring Data REST docs](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting.paging), but it was in relations to making an endpoint pageable
              * The annotation is used in the [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/) tutuorial
        * PagingAndSortingCrudRepository
          * URL Path Params
            * size: number of elements per page, default 20
            * page: which page to return, 0-indexed, defaults to 0
            * sort: attribute(s) to sort results by, default is by id
              * ,asc: ascending sort
              * ,desc: descending sort
          * Example usage
            * http://localhost:8080/tours?size=3&page=1&sort=title,asc
          * Response will include links to first, last, and next

## Spring MVC

* Spring Data REST vx Spring MVC
  * Spring MVC
    * Client Request --> MyRestControllerClass --> MyService
  * Spring Data REST
    * Client Request --> Spring Data REST --> MySpringDataRepository
* Why use Spring Web MVC
  * Not using Spring Data REST Repositories
  * API launches an algorithmic service, not just data retrieval
  * Hide internal data model (entity schema)
  * Require a business layer service
* Example REST Controller
```java
@RestController // Required
@RequestMapping(path = "/examples") // Sets a base URL for the whole class
public class ExampleController {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Example create(@RequestBody Example example) {
    // By default, the request body is expected to be JSON
    // ResponseStatus overrides the default 200 success status with 201
  }

  @GetMapping // Can explicitly set path
  public List<Example> getAllExamples() {

  }

  @GetMapping(path = "/{id}")
  public Example getOneExample(@PathVariable(value = "id") int id) {
    // Do stuff
  }

  @PutMapping(path = "/{id}")
  public Example updateAll(@PathVariable(value = "id") int id, @RequestBody Example example) {
    // Do stuff
  }

  @PatchMapping(path = "/{id}")
  public Example updateSome(@PathVariable(value = "id") int id, @RequestBody Example example) {
    // Do stuff
  }

  @DeleteMapping(path = "/{id}")
  public Example delete(@PathVariable(value = "id") int id) {
    // Do stuff
  }
}
```
* Rest Controller APIs to not automatically create hypermedia links like responses from Spring Data REST do
  * Need to code these yourself if desired
  * Page related metadata is included in the paged response, but not the links observed from Spring Data REST

## Pivoting to MongoDB

* Check out the [first-spring-boot-micro-service-mongo](https://github.com/elandt/devTools/tree/first-spring-boot-micro-service-mongo/java/spring/creating_your_first_spring_boot_microservice) branch