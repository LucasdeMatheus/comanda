package com.myproject.comanda.domain.product;

import com.myproject.comanda.domain.waiter.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

