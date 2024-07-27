package com.jway.products.http.controllers;


import com.jway.products.http.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public ResponseEntity<?> sayHay(){
        return new ResponseEntity<>(new MessageResponse("products service :Hay"), HttpStatus.OK);
    }

    @GetMapping("/connect")
    public ResponseEntity<?> connect(){

        MessageResponse messageResponse=restTemplate.getForObject("http://CATEGORIES/api/v1/categories",MessageResponse.class);
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }



}
