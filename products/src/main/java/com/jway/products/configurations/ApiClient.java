package com.jway.products.configurations;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



@Configuration
public class ApiClient {


    @Bean
    @LoadBalanced
    public RestTemplate getApiClient(){
        return new RestTemplate();
    }


}
