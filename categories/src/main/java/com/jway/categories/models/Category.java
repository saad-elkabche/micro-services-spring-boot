package com.jway.categories.models;

import jakarta.persistence.*;

@Table(name = "categories")
@Entity
public class Category {
    @Id
    @SequenceGenerator(
            name = "categories_sequence",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "categories_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private long id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
