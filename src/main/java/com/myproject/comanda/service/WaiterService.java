package com.myproject.comanda.service;

import com.myproject.comanda.domain.waiter.Waiter;
import com.myproject.comanda.domain.waiter.WaiterDTO;
import com.myproject.comanda.domain.waiter.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaiterService {
    @Autowired
    private WaiterRepository waiterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // cria login pra um usuario
    public ResponseEntity<Waiter> create(WaiterDTO waiterDTO){
        Waiter waiter = new Waiter(
                passwordEncoder.encode(waiterDTO.password()),
                waiterDTO.login()
        );

        waiterRepository.save(waiter);
        return ResponseEntity.ok(waiter);
    }

    // retorna todos os logins
    public ResponseEntity<List<Waiter>> tolist(){
        List<Waiter> waiters = waiterRepository.findAll();
        return ResponseEntity.ok(waiters);
    }

    //
}
