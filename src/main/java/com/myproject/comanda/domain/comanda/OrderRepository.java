package com.myproject.comanda.domain.comanda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumberAndClosedIsFalse(Integer orderNumber);

    List<Order> findAllByOpenedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}


