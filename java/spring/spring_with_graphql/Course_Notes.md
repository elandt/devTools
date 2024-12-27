# Course Notes

## What you Need/Need to Know

* Java
  * JDK 17
  * Basic understanding of the Java language
  * Maven or Gradle
  * IDE
* Spring
  * Spring Framework
  * Spring Web
  * Spring Data
  * Spring Boot
* GraphQL
  * Very Basic Knowledge
  * Playground

## Getting to Know GraphQL

* Power of GraphQL
  * Considered by many to be the most logical way to build an API consumed by both JS apps and mobile apps
  * What is GraphQL
    * An API (application programming interface)
    * Query Language
    * Runtime for answering queries
    * Fetches the data you ask for
  * Why we use GraphQL
    * Single request
      * Could return one or many results from the same endpoint
      * REST would need at least 2
    * Filter responses to only what you need
      * REST always gives the whole object whereas GraphQL allows you to trim to only the fields you need
    * Reduced downstream traffic
    * Less client configuration
    * Type safety
    * Versioning
      * Schemas and Registries are used over traditional API versioning
* GraphQL in Java
  * GraphQL Java
    * Independently maintained
    * Open source software project
    * MIT License
    * Discussions on GitHub/Twitter/SO
  * Using GraphQL Java
    * It is the core library and can be used as a standalone project
    * Need to provide HTTP services
      * The core library does not handle exposing GraphQL to consumers
    * Provides official Spring Boot integration
    * Other abstractions available
  * Similar to MVC
    * GraphQL leverages MVC-like patterns
    * Can leverage Spring Data GraphQL respositories
      * If you want to expose CRUD operations directly through an MVC portal
      * Not part of this course
    * Spring Boot Starter wires common defaults
    * Static schema definition
      * Schema is the contract
* GraphQL Terminology
  * Queries and Mutations
    * Objects: Highest level resource
    * Fields: Element of an object
    * Queries: The information returned, i.e. what you're asking for
    * Mutations: How you change the data
    * Fragments: Reusable components for queries
    * Directives: Used to build custom query operations
  * Schemas and Types
    * Type system: How objects are defined
      * Looks similar to JSON, but it's not
    * Object types: Definition of an object returned and input types
    * Arguments: What is passed to a query or mutation
    * Query and mutation types: Special types within a schema
    * Scalars, lists, and enumerations: types of fields
    * Interfaces: The structure used with common fields shared between objects
* GraphQL Schemas
  * Several free and available schemas exist
  * [Apollo GraphQL Countries API](https://studio.apollographql.com/public/countries/home?variant=current)
    * [The API itself](https://countries.trevorblades.com/graphql)
* Using the playground
  * [GraphQL Playground for Chrome](https://chromewebstore.google.com/detail/graphql-playground-for-ch/kjhjcgclphafojaeeickcokfbhlegecd) - used in course, behavior is a bit odd in Edge
  * [Altair GraphQL Client](https://microsoftedge.microsoft.com/addons/detail/altair-graphql-client/kpggioiimijgcalmnfnalgglgooonopa) - option for Edge
  * Activity in course

  ```graphql
  query {
    countries {
      name
      emoji
      code
    }
  }

  # Order of fields in query determines the order of the fields in the response
  query {
    countries {
      name
      code
      emoji
    }
  }

  query {
    country(code: "US") {
      name
      code
      emoji
      phone
    }
  }

  # Will return a null country - REST would return a 404
  query {
    country(code: "UB") {
      name
      code
      emoji
      phone
    }
  }
  ```

## Building GraphQL APIs

* Setting up the project
  * Java 17
  * Spring
    * Course uses 2.7.0 (snapshot)
    * I'm using 3.4.1
  * Dependencies
    * Spring
      * Web
      * GraphQL
      * Data JPA
      * Actuator (optional)
    * H2 (embedded db)
  * Distribution: JAR
* Creating the Schema
  * Schema/Contract first development is "the way to go"
  * `src/main/resources/graphql` is the conventional location for defining the schema for Spring
    * This was created out of the box by Spring Initializr due to including the `Spring for GraphQL` dependency
    * It's possible to change this default...but really, why would you
  * Schema files use the file extension `.graphqls` or `.gqls`
  * A trailing `!` indicates that a field or response value is required/will always be present
* Creating a Spring Data repository
  * Can back a GraphQL service with any data source
    * Including REST services, using RestTemplate as a data source to expose a GraphQL Layer
    * Course will go directly to the database
* Creating the GraphQL controller
  * Rather than `@GetMapping`, `@PostMapping`, etc, GraphQL endpoints use `@QueryMapping`
  * Rather than `@PathVariable` to define inputs, GraphQL endpoints use `@Argument`
    * With Spring 6.1+ addressing [Parameter Name Retention](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-6.1-Release-Notes#parameter-name-retention) is required
      * This applies for both `@PathVariable` with REST endpoints and `@Argument` with GraphQL endpoints
      * To allow running as a Spring Boot app, I've used the approach where you add the `.settings/org.eclipse.jdt.core.prefs` file with the `org.eclipse.jdt.core.compiler.codegen.methodParameters=generate` property defined
  * Unless the `@QueryMapping` specifies a deviation, the name of the method _needs_ to match the query name defined in the schema
  * Some example queries and responses

  ```graphql
  # Returns the id, firstName, lastName, and email fields of all customers
  query {
    customers {
      id
      firstName
      lastName
      email
    }
  }

  # Response
  {
    "data": {
      "customers": [
        {
          "id": "1",
          "firstName": "Carol",
          "lastName": "Shaw",
          "email": "cshaw0@mlb.com"
        },
        {
          "id": "2",
          "firstName": "Elizabeth",
          "lastName": "Carr",
          "email": "ecarr1@oracle.com"
        },
        {
          "id": "3",
          "firstName": "Ernest",
          "lastName": "Ramos",
          "email": "eramos2@plala.or.jp"
        },
        {
          "id": "4",
          "firstName": "Jane",
          "lastName": "Carter",
          "email": "jcarter3@harvard.edu"
        }
        #...
      ]
    }
  }

  # Returns the id, firstName, lastName, and email fields of customer 2
  query {
    customerById(id: 2) {
      id
      firstName
      lastName
      email
    }
  }

  # Response
  {
    "data": {
      "customerById": {
        "id": "2",
        "firstName": "Elizabeth",
        "lastName": "Carr",
        "email": "ecarr1@oracle.com"
      }
    }
  }

  # Same as the last one, but also return the address and zipCode fields
  query {
    customerById(id: 2) {
      id
      firstName
      lastName
      email
      address
      zipCode
    }
  }

  # Response
  {
    "data": {
      "customerById": {
        "id": "2",
        "firstName": "Elizabeth",
        "lastName": "Carr",
        "email": "ecarr1@oracle.com",
        "address": "3934 Petterle Trail",
        "zipCode": "78732"
      }
    }
  }

  # Query the same info as the last, but search by the email rather than the id of the customer
  query {
    customerByEmail(email: "ecarr1@oracle.com") {
      id
      firstName
      lastName
      email
      address
      zipCode
    }
  }

  # Response
  {
    "data": {
      "customerByEmail": {
        "id": "2",
        "firstName": "Elizabeth",
        "lastName": "Carr",
        "email": "ecarr1@oracle.com",
        "address": "3934 Petterle Trail",
        "zipCode": "78732"
      }
    }
  }
  ```

* Mutations
  * Uses `@MutationMapping`

  ```graphql
  # Adds a customer with the specified info, and declares that the response should include the id, firstName, lastName, and email fields
  mutation {
    addCustomer(input: {
      firstName: "John"
      lastName: "Doe"
      email: "jdoe@example.com"
      phoneNumber: "2153363600"
      address: "3601 S Broad St"
      city: "Philadelphia"
      state: "PA"
      zipCode: "19148"
    })
    {
      id
      firstName
      lastName
      email
    }
  }

  # Response - containing only the finds specified in the request
  {
    "data": {
      "addCustomer": {
        "id": "1001",
        "firstName": "John",
        "lastName": "Doe",
        "email": "jdoe@example.com"
      }
    }
  }
  ```

* Errors
  * Can return multiple errors with GraphQL
    * Not what's being done in the course, but possible
  * Custom error handler `extends DataFetcherExceptionResolverAdapter`
  * Example `HPlusExceptionHandler`
    * Wouldn't want to expose the SQL in a production setting like we do here

* Challenge
  * Extend the API to implement as many of the additional tables from the `schema.sql` as you want
  * Add additional queries
    * You have serveral options: do one or do them all
    * Leverage JPA joins, where possible
    * Utilize the `schema.sql` file to guide you
    * Take it as far as you can
  * Example Queries

  ```graphql
  query {
    orders {
      id
      customer {
        id
        firstName
        lastName
      }
      salesperson {
        id
        firstName
        lastName

      }
    }
  }

  # Response
  {
    "data": {
      "orders": [
        {
          "id": "H2-1203",
          "customer": {
            "id": "929",
            "firstName": "Timothy",
            "lastName": "Kim"
          },
          "salesperson": {
            "id": "19",
            "firstName": "Benjamin",
            "lastName": "Sims"
          }
        }
      ]
    }
  }

  # Multi-level nesting in a query
  query {
    customerById(id: 929) {
      id
      firstName
      lastName
      email
      orders {
        id
        salesperson {
          id
          firstName
          lastName
          orders {
            id
          }
        }
        orderLines {
          id
          product {
            id
            name
            size
            variety
            status
            price
          }
          quantity
        }
      }
    }
  }

  # Response
  {
    "data": {
      "customerById": {
        "id": "929",
        "firstName": "Timothy",
        "lastName": "Kim",
        "email": "tkimps@sciencedirect.com",
        "orders": [
          {
            "id": "H2-1203",
            "salesperson": {
              "id": "19",
              "firstName": "Benjamin",
              "lastName": "Sims",
              "orders": [
                {
                  "id": "H2-1203"
                }
              ]
            },
            "orderLines": [
              {
                "id": "1",
                "product": {
                  "id": "MWBLU20",
                  "name": "Mineral Water",
                  "size": 20,
                  "variety": "Blueberry",
                  "status": "ACTIVE",
                  "price": 1.79
                },
                "quantity": 4
              },
              {
                "id": "2",
                "product": {
                  "id": "MWSTR32",
                  "name": "Mineral Water",
                  "size": 32,
                  "variety": "Strawberry",
                  "status": "ACTIVE",
                  "price": 3.69
                },
                "quantity": 2
              }
            ]
          }
        ]
      }
    }
  }
  ```

## What's next

* Review
  * Learned a bit about GraphQL
  * Implemented a simple schema
  * Leveraged JPA
    * Though there are many options for data sources here
  * Wired with controllers
    * Similar to MVC
  * Customized error handling
* Next Steps
  * Build your own from scratch
  * Leverage a Rest-based service, instead of JPA
  * Add a JS frontend
  * Look into the batching APIs that come with GraphQL Java
