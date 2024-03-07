package com.mat.taksov.home;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public MyClass getEnvName(){
        return new MyClass("prod");
    }
}
