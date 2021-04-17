package com.rogers.codingassignment.config;

import com.rogers.codingassignment.service.PersonService;
import com.rogers.codingassignment.service.impl.DbPersonServiceImpl;
import com.rogers.codingassignment.service.impl.HashMapPersonServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {

    @Value("${server.uri.url}")
    private String baseURL;

    @Bean
    @Qualifier("baseURL")
    public String baseURL() {
        return baseURL;
    }


//    @Bean
//    public PersonService personServiceMeow() {
//        return new DbPersonServiceImpl();
//    }

}
