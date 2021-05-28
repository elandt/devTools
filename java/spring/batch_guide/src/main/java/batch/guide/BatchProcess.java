package batch.guide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchProcess {

    public static void main(String[] args) throws Exception {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchProcess.class, args)));
    }
}
