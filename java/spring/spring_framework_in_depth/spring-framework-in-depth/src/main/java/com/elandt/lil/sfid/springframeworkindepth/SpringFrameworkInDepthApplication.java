package com.elandt.lil.sfid.springframeworkindepth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// import com.elandt.lil.sfid.springframeworkindepth.config.ApplicationConfig;
import com.elandt.lil.sfid.springframeworkindepth.service.OutputService;

@SpringBootApplication
public class SpringFrameworkInDepthApplication {

	public static void main(String[] args) throws InterruptedException {
		// SpringApplication.run(SpringFrameworkInDepthApplication.class, args);
		
        OutputService outputService = SpringApplication.run(SpringFrameworkInDepthApplication.class, args).getBean(OutputService.class);;

        for (int i=0;i<5;i++){
            outputService.generateOutput();
            Thread.sleep(5000);
        }
	}

}
