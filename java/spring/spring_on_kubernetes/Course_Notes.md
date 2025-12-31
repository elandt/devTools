# Course Notes

## Intro

### What you Need to Know

* Spring
  * Spring Framework
  * Spring Boot
* Java
* Containers
  * Understand what they are and why we use them
  * Building and running containers
  * Some Kubernetes experience is helpful
* Processes and Operations
  * Understand the value of continuous integration and continuous delivery (CI/CD)
  * Understand the benefits of automation
  * Have some exposure to operations

### Development Environment

* GitHub Codespaces is available - defined via the `devcontainer.json`
* Given that the course files include a devcontainer config, I'm tweaking that to allow for local development rather than using Codespaces
  * In order for this to work, we need to set the image
    * GitHub Codespaces has a default image hence why no image definition is provided in the `devcontainer.json` in the original repo files
    * To mimic what Codespaces uses, the `"image": "mcr.microsoft.com/devcontainers/universal:linux",` image definition is added to the `devcontainer.json`
  * The `hostRequirements` key is provided so that the Codespace is set up with the appropriate hardware specs, if running this in a devcontainer locally, your system may or may not have met those requirement
    * If those requirements are not met, you may get a warning showing your specs, and asking if you want to continue
    * I've commented this out so that it's not used when running locally, but still available if wanting to use Codespaces
* To finish off env setup, execute `./scripts/install.sh` in the terminal

### Developer Workflow

* As can be seen in the `Development Environment` section above, this course uses a scripted development environment setup.
* Benefits
  * Faster local development
    * All tools, apps, service, etc. are local
  * No need to be connected
  * Ability to run your system locally
  * Supports a full CI/CD pipeline
  * *NOTE* to do all of this locally requires a fairly powerful machine (for example, the host requirements for the GitHub Codespaces are 8 CPUs and 32GB of memory)

## Cloud Native Spring

### Cloud-Native Applications

* Cloud-native app dev patterns, also called 12- or 15-factor applications, are a set of patterns that have arisen with cloud computing
  * Frank P. Moley III has a course called [Cloud Native Twelve-Factor and Fifteen-Factor Applications](https://www.linkedin.com/learning/cloud-native-twelve-factor-and-fifteen-factor-applications)
* General Ideas
  * Single process
    * App servers could be considered the process, but then have many apps running within
      * This can make operations difficult, and obfuscates what is important to the dev team
    * Kubernetes can deviate from the single process idea depending on your perspective
      * Sidecars and multi-container pods deviate from this idea from the operators perspective
      * Understanding of this is outside the scope of this course, but can be useful to developers
  * Strong development ecosystems
    * Think Prod/Non-prod parity
    * CI/CD practices
    * Can even go so far as having similar setups on developer machines
  * Stateless and concurrent
    * Cloud-native are stateless by design
      * Instances are often ephemeral
  * Rapid startup with graceful shutdown
    * Starting rapidly and gracefully terminating along with concurrency provide a highly available system
  * Environment-driven configuration
    * Config injected into the environment
    * Separates concerns of the app code from the config which may include secrets
      * This can help prevent password leaking
  * Observability
    * Must be a first-class thought in a cloud-native system
* Spring on Kubernetes: Considerations
  * Handling environment variables
    * Some options
      * Inject them into the dockerfiles and translate there to JVM args
      * Use Spring properties directly in environment config
      * Use standard env vars and their naming conventions, then port them to Spring properties in `application.properties`
  * Logging
    * Most log shippers used in Kubernetes-based runtimes work best with JSON formatted logs
    * Easy to solve with Spring, but needs to be considered and accounted for
    * Building common taxonomy of tags in your logs is worth the effort regardless of language or patterns used
  * Telemetry
    * Common taxonomy also important here
      * Ideally shared with the logging taxonomy
    * Having well-instrumented applications is the most important part
      * Technology like OpenTelemetry can be useful here
  * Security
    * Many layers
      * How we build apps
      * How we integrate with authentication and authorization systems
      * How we build our images
    * 15-factor idea on this is: authentication validated at ingress, and authorization validated throughout

### Creating our Application

* `wisdom-api`
  * Course uses maven, I'm using gradle
  * Dependencies
    * Spring Web
    * Spring Boot Actuator
    * Spring Data JPA
    * PostgresSQL Driver
    * Lombok
    * Prometheus

### Preparing for Data Access

* See `application.properties` for the necessary properties
* See `launch.json` for how the env vars that are injected into `application.properties` are defined

### Building Data Access

* See the `data` package
  * Entity - `Customer.java`
  * Repository - `CustomerRepository.java`
### Exposing as a webservice

* See `web` package
  * Controller - `CustomerController.java`

### Challenge

* Add entities for the remaining tables in the schema
* Add corresponding repositories
* Add controllers with
  * getAll
  * getOne (by ID)
  * POST
  * PUT
  * DELETE
* Include Error Handling and Response Codes as appropriate
  * Note on Solution
    * Exception Handling
      * Instructor created custom `BadRequestException` and `NotFoundException` classes that extend `RuntimeException`
      * I simply returned `ResponseEntity` objects with the appropriate status codes when bad requests or element not found occurred
    * Response Codes
      * Instructor exclusively used `@ResponseStatus(HttpStatus.{appropriate_status})` when necessary
      * I opted for returning `ResponseEntity` with the appropriate status originally, then switched to a mixed approach after seeing the instructor's solution

### Logging in Spring

* Need to ensure logs are structured properly when targeting a Kubernetes-type deployment
* Added `net.logstash.logback:logstash-logback-encoder:8.1` to `build.gradle`
  * Created `logback-spring.xml` for defining the config for logging with logback
* When log streaming in a Kubernetes environment, everything gets dumped to standard out from every app
  * In order to reasonably use the logs, we need to ship them to a centralized log management system, like the ELK stack
    * Oftentimes, those only work well when you have JSON-formatted logs

### Metrics in Spring

* Traditionally, aspecting would be a favorable approach
* Advent of native image limits us to no longer favor aspecting
* Added a `@Bean` to define the app name as a common tag across all metrics
* Added timers to `CustomerController.java` - only implemented timing for `getAllCustomers` for now

## Spring Images

### Containerized Workloads

* Kubernetes is an _orchestration engine_
  * Containers are what it orchestrates
* History of Containers
  * They're not new
  * `chroot` introduced in 1979 in Unix 7
    * First major jump into the world of containers and process isolation
    * Conceptually containerization and sandboxing had been around since the '60s
  * LXC - Linux Container (2008)
    * Leverages `cgroups` and `namespaces`
    * Advancements and deviations occurred over the years
      * Warden is one such deviation
  * Docker
    * Leveraged LXC initially, then replaced it with `libcontainer`
    * The ecosystem around Docker is part of what led to the explosion in containers
  * Kubernetes, `containerd`, and beyond
    * `containerd` was dontated to the CNCF
* What are containers?
  * Packaged and isolated runtimes
  * All dependencies included
    * You package it yourself
  * Truly portable and self-contained applications
  * Shared kernel with host (and all other containers running on the host)
  * Separated application processes

### Spring Docker plugin

* Not the instructors preferred way of building images
* Deleted the test that comes with a Spring Initializr app as it's outside the scope of the course to get that test functioning properly with our app setup (the env vars and such)
  * There is a later video that does talk about testing strategies
* Building an image with the Spring Docker plugin
  * Maven: `mvn spring-boot:buildImage`
  * Gradle: `gradle bootBuildImage`
  * The output can be customized, but the customization options are limited
    * [Build Image Customization Docs](https://docs.spring.io/spring-boot/maven-plugin/build-image.html#build-image.customization)

### Building a custom Docker image

* Useful practice for companies to roll their own standard base images for security
  * For example
    * Start with raw Alpine
    * Add the necessary JDK/JRE/build tooling
    * Developers then use the company base image(s) and if there's a security issue in Alpine, the base images get updated and then that update can flow to the downstream images built on those base images
* Created a `Dockerfile` that has stages for building the app as well as running it
* Created `env.list` to provide the env vars for running the docker container
  * Normally this would **NOT** be committed to version control because it has secrets
    * However, given that this is a demo app and these secrets are meaningless, this file will be committed
  * Rather than using `localhost` for `DB_HOST`, we need to execute `ifconfig` to get the `inet` value for the `docker0` network interface
    * Localhost in the context of a container is within the container itself, so our app would not be able to connect to the postgres DB while using `localhost` AND running in a container

### Uploading to a container registry

* Container Resgitries
  * Used to store images similar to how `.jar` files are stored in repositories (think Artifactory)
  * Offer features beyond just storing images, but that's outside the scope of this course
  * In the course, we loaded the image we created into `kind` so that we could synthesize using an image registry
    * Execute `kind load docker-image wisdom-api:0.0.1 --name local` to do this
      * `--name local` defines the cluster within `kind` to load the specified `docker-image` into
    * Check out the [kind local registry docs](https://kind.sigs.k8s.io/docs/user/local-registry) to learn more


### Spring Native images

* Starting to see a shift toward building native images with Java thanks to GraalVM
  * Only getting surface level, if that here
  * Instructor has a course on GraalVM if I want to dive deeper
    * Java Microservices with GraalVM
* Why Native Images?
  * They're smaller
  * Faster than VM-based operations
    * Performance gains in startup time and execution
  * More Efficient
    * Usage of CPU and memory
    * Can run a higher density pod on a Kubernetes node
* Why not use Native Images?
  * Not all code is capable
    * Remember the aspecting note for metrics earlier?
  * No JMX or other tools
    * JVM TI
    * Java agents
    * Flight Recorder
    * Mission Control
  * Large heap loses efficiency
    * Native images use serial garbage collection
    * Large heap sizes can cause major pauses during GC cycles
  * Reflection
    * Most major players have already made this adjustment
  * Lack of thread and heap dumps
* Spring Boot Support
  * Native image support built in via starter
  * Driven by the Spring Boot plugin
  * Output of Maven/Gradle processes
  * Image is the result
    * an OCI image, or a Docker image, essentially

## Deploying to Kubernetes

### Deploying to Kubernetes

* Our deployment for this course will involve
  * Building a `namespace`
  * Adding config and secrets to that `namespace`
  * Deploy a pod - only using one in the course, but normally you'd have multiple for HA
  * Deploy a service in front of the pod to handle load balancing (doesn't do much for us due to only having one pod)
  * Build an ingress port to allow access without port forwarding
  * Add monitoring
  * Part of `namespace`
    * Config
    * Secrets
    * Pod(s)
    * Service
  * Outside of `namespace`
    * Our Postgres DB
    * The Ingress (sort of)
    * The Monitoring

### Building the initial scaffolding

* See the `deployment` folder in `wisdom-api`
  * Note: using a `secret.yaml` that is stored in source control is _not_ how you'd actually want to handle secrets, this approach is purely for the demonstration of a Kubernetes secret and how they're set up
* Some commands that were executed during this portion of the course (execution was from within the `wisdom-api` folder)
  * `kubectl get nodes` - lists all the `nodes`
  * `kubectl get ns` - lists all the `namespaces`
  * `kubectl apply -f deployment/namespace.yaml`
    * This creates the `namespace` we defined in `deployment/namespace.yaml`
    * `kubectl create ns some_name` could also be used to create a `namespace` named `some_name`
  * `kubectl get ns` - listing `namespaces` again to confirm that the "wisdom" `namespace` was created
  * `kubectl apply -f deployment/sercet.yaml`
  * `kubectl get all -n wisdom` - list all resources of the "wisdom" `namespace`
    * Even though we just created the secret, it won't be listed here
  * `kubectl get secrets -n wisdom` - lists the secrets of the "wisdom" `namespace`
    * The value of the secret is not displayed in the output
      * Output includes
        * name
        * type
        * data - how many pieces of data the secret includes, in our case 2 - the username and password for the postgres db
        * aged - how long ago it was created
    * Secrets are Base64 encoded when they're applied

### Building a deployment

* See `deployment/deployment.yaml`
  * For the `DB_HOST` env value, we cannot use `localhost` - must reference it from the Kubernetes perspective
    * In our case, the Postgres DB is accessible as a `Service` in the "postgres" `namespace` in the `cluster` "local", and we're using the _read-write_ version of the service
  * Probes are used by Kubernetes to determine if a system is up or not
    * We used the `/actuator/health/liveness` and `/actuator/health/readiness` endpoints for this
      * Check the Spring Docs for how these can be customized to fit your needs
* To deploy, execute:
  * `kubectl apply -f deployment/deployment.yaml`
* Check the deployment
  * `kubectl get all -n wisdom`
  * `kubectl port-forward -n wisdom wisdom-api-deployment-74b757c8d5-w4c2z 9000:8080` - to forward port 8080 from inside the Kubernetes pod to port 9000 in the devcontainer
    * `wisdom-api-deployment-74b757c8d5-w4c2z` is the pod name
  * `http :9000/services` to call the `/services` endpoint of our app running inside Kubernetes

### Building a service

* See `deployment/service.yaml`
  * When defining the `service` ports
    * `port` is the port that the service listens on
    * `targetPort` is the port that the service directs traffic to
* Commands executed
  * To apply the `service` definition
    * `kubectl apply -f deployment/service.yaml`
  * To check the `service`
    * `kubectl get all -n wisdom`
      * `kubectl get services -n wisdom` - this will list only the `services` rather than all of the resources
    * `kubectl port-forward -n wisdom svc/wisdom-api-service 9000:80` - to forward the port that the service listens on to the port 9000 in the devcontainer
      * NOTE: because of the `targetPort` being set to `8080` (the port our Spring app listens on), the output of the `kubectl port-forward ...` command shows `Forwarding from 127.0.0.1:9000 -> 8080` and `Forwarding from [::1]:9000 -> 8080` which is the same output as when we used the `port-forward` command on the pod itself.

### Adding ingress

* Adding Ingress allows us to send traffic to the pod with our app _without_ needing to port forward
* See `deployment/ingress.yaml`
  * The `annotations` control some aspects of the ingress behavior
    * `nginx.ingress.kubernetes.io/use-regex: "true"` - enables regex matching and operations on the url path
    * `nginx.ingress.kubernetes.io/rewrite-target: /$2` - rewrites the path so that the full path is not sent to the service, but rather only the portion specified in the `path` of our `rule`
      * This is important because the service doesn't know anything about how we're doing the routing
  * The `rules` define the the rules for routing
    * The `path: /wisdom-api(/|$)(.*)` is the regex that is used in conjuction with our "rewrite-target" `annotation` to send a portion of the path to our service
    * The `backend` section of the `rule` defines the routing
* Commands executed
  * To apply the `ingress` definition
    * `kubectl apply -f deployment/ingress.yaml`
  * To check the `ingress`
    * `kubectl get ingress -n wisdom` - lists all `ingresses` for the `namespace` "wisdom"
    * `kubectl describe ingress wisdom-api-ingress -n wisdom` - lists the ingress definition
    * `http :80/wisdom-api/services` - to call the `/services` endpoint of our app
      * NOTE: `/wisdow-api` has been added to our path
        * This is because of our `path` definition on line 14 if `deployment/ingress.yaml`
          * The path must be an absolute path - attempting to use only the regex portion from the path used in the course yields: `The Ingress "wisdom-api-ingress" is invalid: spec.rules[0].http.paths[0].path: Invalid value: "(/|$)(.*)": must be an absolute path`
          * Though it must be absolute, it can be pretty much anything
            * Using `/(/|$)(.*)` is allowed to apply, but I wasn't able to actually use it
              * `http :80//services` - returns a 404
              * `http :80/services` - returns a 404

### Adding monitoring

* The stack provided by the course files includes Prometheus and Grafana
  * Both of these are started by the `scripts/install.sh` script
* See `deployment/monitoring.yaml`
  * Using a `PodMonitor`
    * `ServiceMonitor` is another `kind` that could be used
  * Set our `selector` to match our app label
  * Set our `podMetricsEndpoints` to point at the `/actuator/prometheus` endpoint of our `pod`
    * Because we're working at the `pod` level here, we use
      * Port 8080 for the `targetPort` because that's what our app uses
      * The `/actuator/prometheus` endpoint from the root - no `/wisdom-api` prefix because we're not going through the `ingress`
* Commands executed
  * To apply the `monitoring` definition
    * `kubectl apply -f deployment/monitoring.yaml`
  * To check the `monitoring`
    * `k get podmonitors -n wisdom` - lists the `podmonitors` in the "wisdom" `namespace`
    * `http :80/wisdom-api/customers` - to generate traffic on the one endpoint that we actually instrumented metrics for
    * On the `PORTS` tab in VS Code
      * 30000 - the port for Prometheus
        * Can query for any of the following and see data from the traffic generated with `http :80/wisdom-api/customers`
          * `getAllCustomers_seconds_count`
          * `getAllCustomers_seconds_max`
          * `getAllCustomers_seconds_sum`
      * 31000 - the port for Grafana (user: admin, pw: prom-operator)
      * May need to manually add these ports if you don't see them
* At this point, the system is fully operational with observability built in
  * Can instrument the rest of the timers that were stubbed out earlier in the course
  * Can explore Grafana visualizations of the metrics
  * Can add other types of metrics and observe those

### Intro to Helm

* Though we built out the deployment through basic Kubernetes YAML files, instructor seldom does this in practice
  * Helm is the option they typically use
  * Not a course on Helm, but will get into some of it at an introductory level
* What is Helm?
  * Package manager for Kubernetes
    * Has been in-use throughout the course with the introductory/setup/teardown scripts
  * Templated deployment files
  * Handles the lifecycle of application delivery
    * Delivery
    * Installation and upgrades
    * Rollbacks (when necessary)
* Strategies for Helm
  * Reduce the number of charts
    * Tempting to build charts for each application - most of these will look the same and reducing them will increase throughput and reduce chasing bugs
  * Hard-code the consistent values
  * Template the variables
    * Default Helm charts can highly templatized and this can be confusing
  * Keep them as simple as possible
    * Only add complexity when necessary
  * Automate them if possible
    * Example from instructor
      * Used CI/CD pipeline to take a templated file and build 1 of 3 variations of Helm
      * Built variation is stored
      * Helm charts were never
        * Checked into the repo
        * Manually written
        * Manually dealt with
      * These stored Helm charts were used for every single deployment
* Uses for Helm
  * Software you use in Kubernetes is often delivered via Helm
  * Applications you write that target Kubernetes
    * Can be used directly or with tools like Argo CD or Harness
  * Operators you build
    * Have a Helm chart to deploy it
      * Especially is available to the world
        * Consumers can use it without needing to figure out how to deploy it

## Additional Topics

### Testing strategies

* Unit Testing
  * Leverage embedded databases
  * Leverage dev containers
  * Mock dependencies
  * Utilize test context (Spring's test slices)
* Integration Testing
  * Should be environment agnostic
  * Keep APIs private
    * This means doesn't change access levels just to facilitate testing
    * Can be difficult because they're private
  * Leverage Kubernetes jobs
    * Can use test frameworks like: pytest, go test, JUnit
    * One-time operation
  * Report back to CI/CD pipelines
    * Can be used to block PRs
  * Consider setup/teardown
    * Don't want an ever-growing DB
    * Don't want tests to collide with live data
    * Don't want tests to collide with themselves and start failing with hard to track reasons
  * Consider case of abnormal termination
    * Need to be able to cleanup after the test terminates because the normal cleanup wouldn't have occurred
* User Acceptance Testing
  * Keep outside the cluster
    * Perhaps a remote cluster or server
  * Test major flows through the app
    * Include both Positive and Negative flows
  * Contract testing on public APIs
    * Public APIs at a minimum - ideally private APIs are also tested to ensure you don't break your own system
  * Load testing
    * Ensures that the system doesn't fold under a load spike
    * Load should be higher than what's been observed over some historical period
  * Performance testing
    * Similar to load testing, but focused on the speed of operation not just if the load can be handled
    * With and Without Load
  * Chaos testing
    * Infrastructure, nodes, pods die - your system should test to ensure it can weather these events
* Security testing
  * Don't forget this
  * Don't ignore this
  * Static application security testing (SAST)
  * Dynamic application security testing (DAST)
  * Container scanning
    * Both the containers you create AND the containers that you deploy to your environment

### Automation in CI/CD

* Continuous Integration (CI)
  * Build on all branches
  * Leverage tools tied to source code management (SCM)
    * GitHub and GitHub Actions for example
      * Can register a remote runner on your local Kubernetes cluster to avoid costs from using GitHub's resources for the runner
  * Create scripts for local dev builds
  * Fast feedback
    * Easier to fix a problem you find early in the process
* Continuous Delivery (CD)
  * Deliver branches locally
  * Test branches locally
  * Deploy to non-prod after merge
  * Test everything there
    * See all the tests that were mention above
  * Manual gates
    * Ideally as few as possible because you have appropriate and comprehensive automations and automated testing in place to remove the need for manual gates
    * The last non-prod env before prod is the only place the instructor would want a manual gate, if even there (see previous statement)

### Security considerations

* Containers
  * Secure the lifecycle
    * A common base image can help with this
  * Scan your images
    * Yours
    * Ones you use from external sources
  * Scan your cluster
  * Maintain your container lineage
    * Don't let rogue images in
* Cluster Security
  * Access Control
    * Developers still have to be able to do their job
  * Operation needs
    * There are tools that allow elevation only when necessary
  * Don't expose what should be private
  * Only expose your public through known ingress
    * For example, if you're running a load balancer in your cloud provider, the only way to get to your ingress should be through that load balancer
      * By funneling that traffic, it's easier to monitor the traffic to your services
  * Automate data and system updates
    * You have a track record of what's been done
    * Removes the need to elevate access to perform updates/routine operations
* "Don't expect someone else to do it."

## Conclusion

### Next steps

* Recap
  * Scripted a `kind` environment
  * Built a simple web service
  * Deployed it to Kubernetes
    * Built by hand
  * Leveraged monitoring
  * Dicsussed automation, security, and testing
* Ideas for Additional Learning
  * Learn about Helm
  * Learn more about Kubernetes
  * Build multiple service that talk to each other within the `kind` cluster
  * Build your own cluster and play with it
    * Instructor has a Raspberry Pi cluster with Kubernetes and a small server rack that they can run Kubernetes on
  * Leverage Kubernetes on your machine
    * Another way to play with Kubernetes, especially if you don't have the resources for larger clusters
    * Can leverage the scripted environment from this course
