package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    //TODO : regarder requestmapping
    //TODO : externaliser les fichiers de configs
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
