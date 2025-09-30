package com.myproject.comanda.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myproject.comanda.domain.comanda.Order;
import com.myproject.comanda.domain.item.Item;
import com.myproject.comanda.domain.item.ItemDTO;
import com.myproject.comanda.domain.waiter.Waiter;
import com.myproject.comanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/open/{orderNumber}")
    public ResponseEntity<Order> openOrder(
            @PathVariable Integer orderNumber,
            @AuthenticationPrincipal Waiter waiter) {

        // aqui você já tem o garçom logado
        return orderService.openOrder(orderNumber, waiter.getLogin());
    }


    @PutMapping("/close/{orderNumber}")
    public ResponseEntity<Order> closeOrder(
            @PathVariable Integer orderNumber) {

        return orderService.closeOrder(orderNumber);
    }


    @GetMapping("/get")
    public ResponseEntity<List<Order>> getOrdersOfToday() {

        return orderService.getOrdersOfToday();
    }

    @GetMapping("/get/{orderNumber}")
    public ResponseEntity<Order> getByOrderNumber(
            @PathVariable Integer orderNumber) {

        return orderService.getByOrderNumber(orderNumber);
    }

    @PutMapping("/{orderNumber}/item/add")
    public ResponseEntity<Order> addItem(
            @PathVariable Integer orderNumber,
            @RequestBody ItemDTO item) throws JsonProcessingException {

        return orderService.addItem(orderNumber, item);
    }

    @PutMapping("/{orderNumber}/item/edit/{itemId}")
    public ResponseEntity<Order> editItem(
            @PathVariable Integer orderNumber,
            @PathVariable Long itemId,
            @RequestBody Item updatedItem) throws JsonProcessingException {
        return orderService.editItem(orderNumber, itemId, updatedItem);
    }

    @PutMapping("/{orderNumber}/item/remove/{itemId}")
    public ResponseEntity<Order> removeItem(
            @PathVariable Integer orderNumber,
            @PathVariable Long itemId
            ) throws JsonProcessingException {
        return orderService.removeItem(orderNumber, itemId);
    }
}