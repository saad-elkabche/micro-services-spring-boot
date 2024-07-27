package com.jway.categories.configrations;


import com.jway.categories.models.Category;
import com.jway.categories.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfigration {


    @Bean
    public CommandLineRunner seed(CategoryRepository repository){
        return args ->{
            Category category1=new Category("category 1");
            Category category2=new Category("category 2");
            List<Category> categories=List.of(category1,category2);
            repository.saveAll(categories);
        };
    }

}
