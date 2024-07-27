package com.jway.categories.http.controllers;


import com.jway.categories.http.Responses.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/categories")
public class CategoriesController {

    @GetMapping
    public ResponseEntity<?> sayHay(){
        return new ResponseEntity<>(new MessageResponse("categories service : Hay"), HttpStatus.OK);
    }


}
