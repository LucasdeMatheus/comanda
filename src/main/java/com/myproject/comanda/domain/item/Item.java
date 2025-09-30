package com.myproject.comanda.domain.item;

import com.myproject.comanda.domain.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

public class Item {

    private Long id;
    private Integer quantity;
    private String observation;
    @OneToOne
    private Product product;

    public Item(ItemDTO itemDTO) {
        quantity = itemDTO.quantity();
        observation = itemDTO.observation();
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

