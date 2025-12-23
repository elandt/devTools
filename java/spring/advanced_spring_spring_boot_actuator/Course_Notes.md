# Course Notes

Spring Boot includes a module known as Spring Boot Actuator that helps with monitoring and managing our Spring Boot applications when we push them to production.

## What you should know

* Intermediate to advanced level knowledge of Java
* Familiarity with Spring Boot and Spring Security
* Some knowledge of Docker images and containers
* Knowledge of HTTP, Database, Postman, cli, APIs, etc.

## Intro to Spring Boot Actuator

* Integrates with a variety of monitoring tools (Prometheus, DataCog, New Relic, etc.) by using Micrometer (a vendor neutral app metrics facade)
* Course will use Prometheus, and then build dashboards with Grafana to visualize the data
* `spring-boot-starter-actuator` from `org.springframework.boot` is the dependency required to use actuator
* Default pattern for endpoints is: `/actuator` + `/{id}` so the `health` endpoint is `/actuator/health`
* Some of the endpoints include (see Spring docs for full list and details): 
  * `beans` - lists all the Spring beans in your app
  * `health`** - app health info
  * `info`** - arbitrary health info
  * `metrics`** - shows metrics info for the app
  * ** = will focus on for this course
* `health` is the only http endpoint exposed by default
* `info` displays arbitrary info sourced from places like build files or git property files
* `metrics` includes metrics like cpu usage, memory usage, and much more
* Exposing and Enabling Endpoints
  * _Almost_ all endpoints are _enabled_ by default, but only the `health` endpoint is _exposed_ by deafult
  * Exposing all of the actuator endpoints could be a security risk
    * For the purposes of the demos in this course, we'll expose the endpoints with a wildcard (`*`)
      * `management.endpoints.web.exposure.include=*` added to `application.properties` is how this is done
    * You can also list the endpoints in a comma-separated list
      * i.e. `management.endpoints.web.exposure.include=info,beans,health`
    * Excluding endpoints can be done in the same ways
      * With a wildcard - `management.endpoints.web.exposure.exclude=*`
      * Naming specific endpoints - `management.endpoints.web.exposure.exclude=info,beans,health`
  * Taking an opt-in rather than an opt-out approach can be set up by setting the `enable-by-default` property to `false` and then selectively enabling the endpoints you're interested in
    * `management.endpoints.enabled-by-default=false`
* Show Details - Health endpoint
  * By default the `health` endpoint only shows a status of `up` or `down`
  * This can be configured to be more detailed, and even customized
    * `management.endpoint.health.show-details=always`
    * Can group elements of the health endpoint response
      * Groups are defined using the following pattern
        * `management.endpoint.health.group.{name of group}.include={comma-separated components to include}`
      * You can view just the health info for a specific group by hitting the endpoint `/actuator/health/{name of group}`
* Show applicaiton info with the `/actuator/info` endpoint
  * Env info, build or git info, build timestamp, or many other things
    * Some examples
      * `info.app.name=@project.name@`
      * `info.app.description=@project.description@`
      * `info.app.version=@project.version@`
      * `info.app.java.version=@java.version@`
    * Check docs for specifics on pulling bits of info from various sources dynamically
    * You can also add hardcoded values
      * `info.yo.dawg=We heard you like info, so we put info in your info.`
    * The JSON response groups the info by the dot notation of the property
      * Each dot after `info` in the property name nests the associated value one level
        * In the above examples
          * Everything with `info.app.*` is in a JSON object called `app` with the `version` nested one level deeper in a `java` object
          * The `info.yo.dawg` property results in a different JSON object called `yo` with a nested key-value pair with key `dawg` and the value as the hardcoded string
* Overriding the Actuator Base Pase
  * Uses `management.endpoints.web.base-path=/the/new/base/path`
  * You can also remap specific endpoints
    * For example, `management.endpoints.web.path-mapping.health=healthcheck` will remap the `health` endpoint to `/actuator/healthcheck`
    * You could also pair the base path customization with the path-mapping change to get `/healthcheck`
      * `management.endpoints.web.base-path=/` - applies to all actuator endpoints
      * `management.endpoints.web.path-mapping.health=healthcheck` - only changes the `health` endpoint

## Creating and Securing Actuator Endpoints

* Can create a custom health indicator by creating a component that implements `HealthIndicator`
  * Check out `DbHealthIndicator.java` for an example
* Creating an entirely custom actuator endpoint
  * Some reasons you might do this:
    * Release notes
    * Jira Server integration
    * Monitor an external service
  * Uses the `@Endpoint(id = "your-custom-endpoint")` annotation on the class
  * Uses one or more of the `@ReadOperation`, `@WriteOperation`, or `@DeleteOperation` on methods to provide functionality
    * `@ReadOperation` - signifies a `GET` request
    * `@WriteOperation` - signifies a `POST` request
    * `@DeleteOperation` - signifies a `DELETE` request
* Securing Endpoints with Spring Security
  * Various actuator endpoints can expose data or operations that you don't want open to unauthorized access
    * An example might be the `health` endpoint when it's showing more than the UP/DOWN status

## Metrics and Prometheus

* As apps/systems become more complex, it's increasingly more important to track meaningful metrics about the app's/system's behavior over time
  * Some metrics that may be helpful include:
    * Latency
    * Traffic (number of requests)
    * Errors/Failure rate
  * [Micrometer](https://www.micrometer.io) is a vendor-neutral application metric facade provided by Spring Boot via 
    * Can intergate with monitoring platforms like Prometheus, DataDog, New Relic, etc
    * Custom metrics can be added by leveraging the various types of metrics micrometer exposes
      * Types
        * Timers - how long something take, like a method execution
        * Counters - how many times something occurs, like number of db calls made, or other events that only increase over time
        * Gauges - used to measure values that rise and fall, like memory or cpu usage
        * 
    * Micrometer collects and exposes metrics while a system like Prometheus uses those metrics for monitoring and alerting by scraping and analyzing them
* To integrate with Prometheus, we need to include the `micrometer-registry-prometheus` dependency from `io.micrometer`
  * This will create and expose an `/actuator/prometheus` endpoint
  * To make use of the of the integration with Prometheus, we need to configure what we want it to track, and how often it should scrape the metrics
    * `spring-boot-actuator/src/main/resources/prometheus.yml` is the config file for Prometheus for this course
    * Because I set these course files up with a devcontainer and did not include the `docker-outside-of-docker`, the targets in `spring-boot-actuator/src/main/resources/prometheus.yml` need to be modified as they're defined for running prometheus in a container rather than from the cli like I did with `prometheus --config.file="spring-boot-actuator/src/main/resources/prometheus.yml"`
      * Didn't add `docker-outside-of-docker` feature to devcontainer because of build issues with the devcontainer when attempting to include that feature and not wanting to spend time sorting it out
        * I have provided a `docker-compose.yml` that includes Prometheus, but it would require docker within the devcontainer, or to be run outside the devcontainer
      * Installed the Prometheus feature in the devcontainer instead of `docker-outside-of-docker`, hence the ability to start Prometheus from the command line
* Adding custom metrics
  * Prometheus supports four core types of metrics
    * Counters - values that only increase
    * Gauges - values that rise and fall
    * Histogram - samples observations, like request duration or response time, in configurable buckets
    * Summary - value distribution in percentiles
  * We'll create a custom hit counter metric to track page hits for our app
    * See `StudentController.java` for the `hitCounter`
  * Counters can reset when the app restarts
    * In Prometheus, the `rate` function can be used to account for resets
      * Syntax `rate(metric[##unit_of_time])`
        * ex: `rate(hit_counter_total[5m])`

## Grafana Dashboards

* Grafana is installed in the devcontainer using the devcontainer feature, but I've also provided the a service for it in the `docker-compose.yml`
  * If using the containerized Grafana, set the Prometheus url in the data source config to `http://host.docker.internal:9090`
    * Could use the container name for Prometheus if they're both running in containers and in the same docker network
  * If running from the command line, set the Prometheus url in the data source config to `http://localhost:9090`...I think
    * I wasn't able to sort out running the grafana server from the command line, so I just stuck with running it in a container from outside the devcontainer
* Grafana has [dashboard templates available](https://grafana.com/grafana/dashboards)
  * Spring Boot APM Dashboard (id: 12900) is used in the course demo

## Next Steps

* Courses
  * Introduction to Prometheus by Chris Bailey
  * [Graphite and Grafana: Visualizing Application Performance by Laura Stone](https://www.linkedin.com/learning/graphite-and-grafana-visualizing-application-performance)