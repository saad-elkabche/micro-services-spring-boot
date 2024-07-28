package com.jway.authentication.services;

import com.jway.authentication.http.LoginRequest;
import com.jway.authentication.http.responses.MessageResponse;
import com.jway.authentication.http.responses.RegisterResponse;
import com.jway.authentication.models.User;
import com.jway.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public RegisterResponse registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token=jwtService.generateToken(user);
        return new RegisterResponse(token);
    }

    public RegisterResponse login(LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        if(authenticate.isAuthenticated()){
            Optional<User> user=userRepository.findByEmail(loginRequest.email());
            if(user.isPresent()){
                String token=jwtService.generateToken(user.get());
                return new RegisterResponse(token);
            }
        }
        return null;
    }

    public MessageResponse checkIfTokenValid(String token){
        Date expireDate=jwtService.getExpiredDate(token);
        String email=jwtService.getUserName(token);
        if(new Date(System.currentTimeMillis()).after(expireDate)){
            return new MessageResponse("invalid");
        }
        Optional<User> user=userRepository.findByEmail(email);
        if(!user.isPresent()){
            return new MessageResponse("invalid");
        }
        return new MessageResponse("valid");
    }



}


