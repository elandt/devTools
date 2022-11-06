package com.elandt.lil.sfid.springframeworkindepth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.elandt.lil.sfid.springframeworkindepth.service.OutputService;

@SpringBootApplication
public class SpringFrameworkInDepthApplication {

	public static void main(String[] args) throws InterruptedException {
		
        OutputService outputService = SpringApplication.run(SpringFrameworkInDepthApplication.class, args).getBean(OutputService.class);;

        for (int i=0;i<5;i++){
            outputService.generateOutput();
            Thread.sleep(5000);
        }
	}

}
