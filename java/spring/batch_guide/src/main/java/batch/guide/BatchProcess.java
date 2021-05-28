package batch.guide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* @SpringBootApplication is essentially a shorthand that includes all of the following:
 * @Configuration - tags the class as a source of bean definitions for the application context
 * @EnableAutoConfiguration - tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet
 * @ComponentScan - tells Spring to look for other components, configurations, and services in the, in this case, batch/guide package, letting it find the controllers
 */

@SpringBootApplication
public class BatchProcess {

    public static void main(String[] args) throws Exception {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchProcess.class, args)));
    }
}
