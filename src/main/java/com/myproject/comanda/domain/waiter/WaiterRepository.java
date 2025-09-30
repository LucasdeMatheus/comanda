package com.myproject.comanda.domain.waiter;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Long> {
    Waiter findByLogin(String login); // ou Optional<Garcom> findByLogin(String login);
}

