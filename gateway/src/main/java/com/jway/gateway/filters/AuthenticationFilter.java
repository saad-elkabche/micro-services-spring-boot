package com.jway.gateway.filters;

import com.jway.gateway.http.responses.MessageResponse;
import com.jway.gateway.http.responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private RestTemplate restTemplate;


    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println("===========filter excuted");
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                try {
                    MessageResponse messageResponse = restTemplate.patchForObject("http://AUTHENTICATION/auth/check-token", new RegisterResponse(authHeader), MessageResponse.class);
                    if(messageResponse.message().equals("invalid")){
                        throw new RuntimeException("un authorized access to application");
                    }
                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException(e.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
