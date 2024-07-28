package com.jway.gateway.filters;

import com.jway.gateway.http.responses.MessageResponse;
import com.jway.gateway.http.responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private WebClient.Builder webClientBuilder;


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
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

               return webClientBuilder.build()
                        .post()
                        .uri("http://AUTHENTICATION/auth/check-token")
                        .bodyValue(new RegisterResponse(authHeader))
                        .retrieve()
                        .bodyToMono(MessageResponse.class)
                        .flatMap(messageResponse -> {
                            System.out.println("message : "+messageResponse.message());
                           if(!messageResponse.message().equals("valid")){
                               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                               return exchange.getResponse().setComplete();
                           }
                           return chain.filter(exchange);
                        })
                        .onErrorResume(e -> {
                            System.out.println(e.getMessage());
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        });

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
