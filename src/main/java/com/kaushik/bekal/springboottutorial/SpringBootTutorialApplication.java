package com.kaushik.bekal.springboottutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.kaushik.bekal.springboottutorial")
@EnableJpaRepositories("com.kaushik.bekal.springboottutorial.dao")
public class SpringBootTutorialApplication {

	//Comment added to test branch
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTutorialApplication.class, args);
	}
}
