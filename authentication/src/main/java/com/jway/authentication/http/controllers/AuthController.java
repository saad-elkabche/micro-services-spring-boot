package com.jway.authentication.http.controllers;


import com.jway.authentication.http.LoginRequest;
import com.jway.authentication.http.responses.MessageResponse;
import com.jway.authentication.http.responses.RegisterResponse;
import com.jway.authentication.models.User;
import com.jway.authentication.services.UserService;
import jakarta.ws.rs.HeaderParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity sayHay(){
        return new ResponseEntity<>(new MessageResponse("Auth Service : Hay"), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        RegisterResponse registerResponse = userService.registerUser(user);
        return new ResponseEntity<>(registerResponse,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        RegisterResponse registerResponse=userService.login(loginRequest);
        return new ResponseEntity<>(registerResponse,HttpStatus.OK);
    }

    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestBody RegisterResponse registerResponse){
        try{
            MessageResponse messageResponse=userService.checkIfTokenValid(registerResponse.token());
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(new MessageResponse("inValid"),HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
