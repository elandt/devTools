# Course Notes

## What you need to Know

* Solid foundation and understanding
  * Spring Framework
  * Spring Boot
* Nice to have
  * Experience with
    * Distributed systems
    * Performance tuning
    * APM tools

## Understand Why Observability is Important

* What is observability
  * Operational insight into the behavior of the system
  * Examples might be
    * Montioring network latency, response time, up time
    * Tracking hardware resource usage
  * "the ability to measure a system's current state based on the data it generates, such as log, metrics, and traces" - Rudolf E. Kalman, engineer, mathematian, inventor
  * A way of staying ahead of the issues at the low level, such as code within the app; at the OS and system levels; and at the network level
  * Monitoring vs Observability
    * Monitoring system needs to answer: what's broken? why is it broken?
      * Detects known set of failures
      * When the system is working
      * If something went wrong
    * Observability
      * What the software is doing, and why it is behaving that way
      * Helps answer questions of:
        * What services did a request go through, and where were the performance bottlenecks?
        * How was the execution of the request different from the expected behavior?
        * Why did the request fail?
        * How did each microservice process the request?
      * Observability can help recover from a failure as quickly as possible with early and fast detection rather than eliminating the failure in the first place
* Why care about observability?
  * Distributed systems means that systems failures could occur from any number of failure points
    * Finding the cause of a particular issue can be difficult
  * Need to be able to push changes and be aware of the impact of those changes
  * Observability can enable:
    * Better visibility
      * E2E system visibility
      * Accurate operational insights
      * System behavior over time
    * Better Alerting
      * Detect, isolate, and alert about critical incidents sooner
      * Investigate root causes more accurately and efficiently
    * Better efficiency
      * No need to track down information through third-party companies and apps
      * Reduce mean time to recovery (MTTR), or the average time it takes to recover from a failure
      * Increase speed of delivery
      * More time for innovation
  * Questions observability can help answer
    * How can we make the app faster?
    * How can the app be more efficient?
    * Is the load balancing properly using all cluster nodes?
    * What is the cache hit ratio?
    * How does failure rate compare to the day before?
    * Which service is the slowest, and where is the bottleneck?
  * Classic Monitoring
    * ex. Do I need to investigate this reponse time spike?
  * Observability with AI Analysis
    * ex. The current response time exceeds the auto-detected baseline by 16,000%
* Logging, metrics, and tracing
  * These are the key pillars of useful/successful observability
  * Microservices and distributed systems require collecting insights from different systems and stiching them together to get a wholistic view of a problem
  * Feeding logs, metrics, and tracing into a single place makes for easier observability
  * Logging - details info about individual things that are ongoing in the app
  * Metrics - aggregated info about app features (memory, cpu, response times, etc.)
  * Tracing - sampled info across multiple services (ex. the flow of a single request through the system)
    * Details about a particular request
    * View flow through the system and find bottlenecks
    * Makes observable system more effective and allows for identification of the root cause of an issue
    * Probably the most important piece of observability
    * Allows for understanding of the causal relationship in the microsservice architecture and follow the issue from the effect to the cause, and vice versa

## Logging

* The problem with basic logging (basic logging = local, on-disk logging for a particular app/service instance)
  * Tedius to navigate and reconstruct a single transaction
  * Can be intermixed in an HA environment
  * How to solve: Use a centralized log service/aggragation, ex. the ELK stack
    * Logstash - collect and transform
    * Elasticsearch - search and analyze
    * Kibana - visualize and manage
* Logging and Spring
  * Spring handles logging config ootb
    * Can be customized
    * Log level set to `INFO` by default
  * Can change Log Level at runtime
    * Logback has an auto-scan feature that can check for config changes
    * Using Spring Boot Actuator
    * Using Spring Boot Admin
      * A web app that can allow various admin operations including changing log levels
    * Spring Cloud Config
    * Spring Cloud Sleuth

## Metrics

* Usage of Metrics
  * Can't improve what you can't measure
  * Counts or measurements that are aggregated over a period of time
  * Originate from a variety of sources
  * Describe specific facts about the system, quantifying essential data
  * Examples
    * How much time do requests to a particular endpoint take?
    * How many people are experiencing errors?
    * What is the average time (or 99th percentile) for a given request?
    * How many people have signed up?
    * How many people have clicked the "checkout" button?
  * Alerts
    * Can establish baselines and thresholds to trigger alerts with system behavior deviates in a meaningful way
  * Trends
    * How has the system behaved over the last hour? Since the last deployment?
  * Metrics are the main source of data for performance tuning
  * Verifies system architecture is configured properly - resource allocation is appropriate for the system
  * Centralized metrics system
    * Time series DB
      * Prometheus
      * DynaTrace
    * Visualize
      * Grafana
* Metrics in Spring
  * Spring Boot Actuator
    * `/actuator/health` - are my dependencies good?
    * `/actuator/metrics` - uses micrometer
      * Timers
      * Counters
      * Histograms
      * Average
      * Metrics are dimensional
        * Dimensions might include
          * URI
          * HTTP Status Code
          * HTTP Method invoked
        * Corresponding metric might be - Response Time
      * Dimensional metrics
        * Enable in-depth analysis and querying
        * Easier to capture and drill down
      * Can then be viewed using a monitoring system of choice
        * Elastic APM
        * Prometheus
        * Dynatrace
        * New Relic
        * Wavefront
      * Easy to implement and configure
        * Add the appropriate micrometer registry for your monitoring system
        * Configure the necessary properties
          * Possibly specific meteric exposure, or the uri of the monitoring system
      * Can define custom metrics
* Observability with Wavefront
  * Has a free tier
  * Doesn't require changing code
  * Spring can automatically create a Wavefront account
  * Seemlessly integrates with Spring Boot apps
    * Included in Spring boot Starter
  * Requires 2 dependencies
    * `wavefront-spring-boot-starter`
    * `spring-cloud-starter-sleuth`
  * Features
    * Large number of metrics ootb
    * Tracing support between Spring components

## Tracing

* Distributed Tracing
  * Goal is to capture
    * Hierarchy
    * Timing info (spans)
    * Metadata (service name)
  * Tracing reported to a tracing backend (time series DB), like Wavefront
* Tracing in Spring
  * Spring Cloud Sleuth (`spring-cloud-starter-sleuth`)
    * Integrated with Spring Boot
    * Enables distributed tracing
    * Provides simple facade over distributed tracing systems (ex. Zipkin or Wavefront)
    * Implementation
      * Add `spring-cloud-starter-sleuth` dependency
      * Auto-configuration
        * Spans - timing info
        * Traces (sampling) - how many to keep
        * Baggage - remote fields - if they're sent
        * Which libraries are traced
    * Sampling can have negative consequences
      * Increased memory usage
      * Increase monitoring system load and/or costs
    * `spring.sleuth.sampler.probability` (1.0 = 100%)
      * Probability of requests that should be sampled
    * `spring.sleuth.sampler.rate` (default 10/second)
      * A rate per second can be a nice choice for low-traffic endpoints as it allows you surge protection
    * Tracing backends may have different algorithms for sampling
      * Wavefront (and probably others) allow customizing this in their application directly

## Conclusion

* Correlate Logging, Metrics, and Tracing
  * Traces can be extensions of logs
    * TraceIds might be able to be used to find corresponding logs
  * Examples of correlations that may provide key insights pointing to a root cause
    * "Increased error rate is highly correlated with service.version = v2.4.1"
    * "Extreme latency is highly correlated with tomcat.node = 3"
    * "Traffic spike is highly correlated with username = john@gmail.com"
  * Traditional Alerts vs AI Smart Alerts
    * Traditional
      * CPU spikes above threshold every day at 4:00 AM - alerts sent daily
    * AI Smart Alerts
      * Every day at 4:00 AM, scheduled tasks are running, causing high CPU usage - no alerts sent
  * Modern observability taking advantage of ai-assisted alerting can reduce wasted time by alerting only on things that are outside the norm given the context that it has available
    * By shifting the cognitive burden onto machines, developers are able to effectively manage systems
* Next Steps
  * Other course by this instructor
    * [Java: Serverless Applications on AWS](https://www.linkedin.com/learning/java-serverless-applications-on-aws)
    * [Advanced Spring: Effective Integration Testing with Spring Boot](https://www.linkedin.com/learning/advanced-spring-effective-integration-testing-with-spring-boot)
    * [Advanced Spring: Application Events](https://www.linkedin.com/learning/advanced-spring-application-events)
