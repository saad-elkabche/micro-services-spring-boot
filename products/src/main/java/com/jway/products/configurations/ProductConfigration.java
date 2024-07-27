package com.jway.products.configurations;


import com.jway.products.models.Prodcut;
import com.jway.products.repositories.ProductsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfigration {

    @Bean
    public CommandLineRunner seed(ProductsRepository productsRepository){
        return args -> {

            Prodcut prodcut1=new Prodcut(
                    "prod 1",
                    "description 1",
                    200,
                    1
            );
            Prodcut prodcut2=new Prodcut(
                    "prod 1",
                    "description 1",
                    200,
                    1
            );
            Prodcut prodcut3=new Prodcut(
                    "prod 1",
                    "description 1",
                    200,
                    1
            );

            List<Prodcut> prodcuts=List.of(prodcut1,prodcut2,prodcut3);

            productsRepository.saveAll(prodcuts);

        };
    }

}
