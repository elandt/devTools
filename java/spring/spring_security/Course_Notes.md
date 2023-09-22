# Course Notes

## What you Need/Need to Know

* Java
  * JDK 17 used in the course
* Spring
  * Base understanding of Spring
  * Base understanding of Spring Boot
  * Understanding of Spring Web
  * Basic knowledge of Thymeleaf with Spring Boot
* Maven
  * Used by instructor
  * I'm using Gradle
* Docker (Not required but used by instructor)
* IDE
* Additional Software
  * Command line
  * HTTPie (can use cURL as well)
  * Web browser

## Introduction to Spring Security

* Security should be tackled in a layered fashion
  * Allows for securing, monitoring, and tuning of distinct focus areas
  * Good security layers
    * Hardware and Media (lowest level)
    * Network (VLANs, Security Zones, Firewalls, etc)
    * Operating System
    * Applications (this is where Spring Security comes in)

### What about Spring Security

* Provides J2EE/JakartaEE application security services
* Designed for enterprise apps and internet-facing apps
  * Spring Security can be used without being built into a Spring or Spring Boot app
* Provides the authentication (who) and authorization (what)

### Understanding Authentication and Authorization

* Authentication (AuthN)
  * Process of verifying a **_principal_** (determining the who)
    * More specifically, determining that a principal is who it claims to be
    * Goes beyong simple username/pasword scenarios
  * **Principals** can be humans or machines
    * User to system/machine
    * System/machine to system/machine
  * Spring provides ootb support for several types of authentication
    * HTTP Basic
    * HTTP Digest
    * x509 certificate based
    * Form-based
    * LDAP
    * Active Directory
    * OpenID
    * Jasig CAS (Central Authentication Service)
    * JAAS
    * Kerberos
    * SAML
    * ...and others
* Authorization (AuthZ)
  * Determines what the **principal** can or cannot do
    * What they're _authorized_ to do
  * AuthZ is based on AuthN
    * Have to AuthN before you can AuthZ
  * AuthZ is often called access control
  * Supports
    * Web request AuthZ
    * Method invocation
    * Domain object instance access control
      * Useful in dealing with PII, Fincanial, and Healthcare relate info

### Intro to the project

* Spring Web MVC monolithic app
* Uses Thymeleaf
* Embedded H2 db that loads on start-up
* Simple Spring Data repositories

### Storing Passwords

* Never store plain text
* Never encypt user passwords
* Use a proper, cryptographically sound one-way has
  * SHA-256 is no longer considers secure due to the possibility of brute force attacks by high performance GPUs and the like
  * Spring Boot supports multiple
    * BCrypt is the default at time of working on the course

### Applying Authorization

* Added thymeleaf-extras-springsecurity6 dependency
* Added a `Principal` to the `getUser` endpoint
* Added info and custom messages to the error page
* Added request matchers to the security config to requre specific roles for various endpoints
* Added an authority mapper to allow converting the case of the Authority to a specific case (UPPER in this instance) so that it doesn't matter how it's stored in the database

### AuthN

#### Basic Auth - `admin-web`

* Basic Auth doesn't allow for logging out
  * Session based --> No logoff/logout support
  * RFC 2617 defines basic auth
  * Base64 header is encrypted when TLS is used, so no real security implications

#### Form-based Authentication - `admin-web`

* No spec for Form-based AuthN
* Allows you to customize the form
* Allows for more seamless view in application
* Provides "Remember Me" options, if implemented

#### Lightweight Directory Access Protocol (LDAP) - `admin-web`

* User directory structure
* Built into many OSs
* Interoperability
* Scalability

##### Spring Security LDAP

* `spring-security-ldap` project
* Full support for native LDAP operations (including outside authentication)
* Password-hashing algorithms included

##### Internal LDAP

* Course uses embedded LDAP
* Can use OpenLDAP if you prefer, via Docker or provided by your OS, if available
* Active Directory in _not_ LDAP, but can use LDAP for AuthN

##### LDAP Use Cases

* Not all uses of LDAP are security related
* `spring-ldap-core` project
  * `spring-boot-starter-data-ldap` also exists and it includes the above
* Not always security focused

###### LDAP Operations

* Query LDAP (filters)
* Search and Dynamic DN (distinguished name) creation
* Binding, update, and delete
* Repository pattern also exists

###### Real-World Use Cases

* Onboarding systems
* Human resources management systems
  * Particularly communication related
* Human resources directory systems
* Other tree-structured data

#### Active Directory (AD) for Authentication

* It's _not_ LDAP
* AD implements an LDAP API
* Directory Services is exposed by AD Lightweight Directory Services (AD LDS) as an LDAP interface
* AD provides more services than just LDAP-related ones

##### Authentication with AD

* Uses standard and nonstandard methods
* Configuration should be very familiar - similar to LDAP
* Nested groups are considered a feature of AD
  * Allows cascading permissions
  * Don't work natively out of the box in Spring Security
  * SEC-1823: Spring Security ticket on the topic of nested groups (includes an implementation step)
* Authentication Provider
  * `ActiveDirectoryLdapAuthenticationProvider`
    * Used in the `@Autowired configure` method in your security config
    * Mostly the same as the standard LDAP AuthN Provider
    * Group to Role matching involves some extra work

#### OAuth2 - `admin-client`

* Protocol and framework for providing access to HTTP services
* Often used for third-party access
  * Social media, for example
* Can be used for system-to-system communications in standalone mode or on behalf of a user
  * Most common use case of the instructor

##### Parts of OAuth2

* Resource owner - often the user
  * Resource - The data actually being protected
  * Example - Google profile data is a resource, and the owner is Google
* Client - application requesting access to a resource
* Resource server - hosts protected data and accounts
  * From Google example:
    * Resource Server would be Google's profile system
* Authorization Server - service that grants tokens

##### Token Types

* Access Token - the secret and often short-lived token that identifies a user and gets their scopes
* Refresh Token - longer-lived token used to renew the Access token when it expires
  * Example
    * Access token expires in 1 day
    * Refresh token expires in 7 days
    * Login creates/retrieves and uses access token
    * Subsiquent visits after the day expires use the refresh token to renew the access token and get a new one
    * If both have expired, user must login/grant access again
* Scopes provies rights associated with the access token

##### Grants

* Several types that impact flows
* Authorization code grant is most common
  * Commonly seen with social media - granting access to someone on your behalf
* Impicit grants are common in web apps and mobile apps
* Client credentials grant is useful in system-to-system communications
  * Each system gets a client id and secret to be used to obtain an Access Token

##### OAuth2 and Spring

###### CommonOAuth2Provider

* Provides native support for Okta, Google, Github, and Facebook
  * Using Github in this course
* Property-based config in Spring Boot
* Client-side OAuth integration

###### Authorization Server

* Provides authorization services to the system
* `@EnableAuthorizationServer`
* `AuthorizationServerConfigurerAdapter` used to configure it
* Supports various grant types

###### Resource Server

* Provides the resources being protected
* `@EnableResourceServer`

###### OAuth2 Client

* Full client-side support
* `@EnableOAuth2Client`
* `Oauth2RestTemplate` provides much of the scaffolding
* Supports various grant types

##### Using GitHug as an OAuth Provider

Need to set up an app in your Github account.

1. Navigate to Settings > Developer Settings > GitHub Apps.
2. Click `New GitHub App`
3. Give the app a name
4. Set the `Homepage URL` to `http://localhost:8080`
5. Set the `Callback URL` to `http://localhost:8080/login/oauth2/code/github`
6. Under `Webhook`, uncheck `Active`
7. Click `Create GitHub App`

In `applicaiton.properties`

1. Set `spring.security.oauth2.client.registration.github.client-id` to the client id for the app created above
2. Generate a secret key in the GitHub app
3. Set `spring.security.oauth2.client.registration.github.client-secret` to that generated secret key
4. **_`NEVER`_** commit these values to source control, these are credentials.

#### Security in Spring WebFlux - `bones`

##### `@EnableWebFluxSecurity`

* Basic config maps everything to security
* `SecurityWebFilterChain` provides more fine-grained control
* `MapReactiveUserDetailsService` provides handle to `UserDetailsService`

##### Principal

* Security model still based on principal
* Inject the `Mono<Principal>` into methods where you want a handle to it
  * Can't just inject the `Principal` as we could outside of WebFlux
* Still provides core functionality...just wrapped in `Mono`

## What's Next

### Review

* Intro to Spring Security
  * Concepts and some terminology
* Secured a web application
* Integrated with LDAP
* OAuth2
* Reactive

### Get Involved

* Find a local Spring user group
* Join the online community at Stack Overflow
* Follow the contributors and advocates on Twitter (X)
* Commit to the project
