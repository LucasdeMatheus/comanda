package com.myproject.comanda.Controller;


import com.myproject.comanda.domain.waiter.Waiter;
import com.myproject.comanda.domain.waiter.WaiterDTO;
import com.myproject.comanda.infra.DataTokenJWT;
import com.myproject.comanda.infra.TokenService;
import com.myproject.comanda.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class WaiterController {

    @Autowired
    private WaiterService waiterService;


    @PostMapping("/create")
    public ResponseEntity<Waiter> create(@RequestBody WaiterDTO waiterDTO){
        return waiterService.create(waiterDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Waiter>> tolist(){
        return waiterService.tolist();
    }

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity toLogin(@RequestBody WaiterDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Waiter) authentication.getPrincipal());

        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));

    }
}
