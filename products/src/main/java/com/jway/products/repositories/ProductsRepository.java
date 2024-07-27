package com.jway.products.repositories;

import com.jway.products.models.Prodcut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Prodcut,Long> {
}
