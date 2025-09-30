package com.myproject.comanda.domain.comanda;

import com.myproject.comanda.domain.waiter.Waiter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // "order" é palavra reservada SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Waiter waiter;

    private Integer orderNumber;

    private LocalDateTime openedAt;

    private LocalDateTime closedAt;

    private BigDecimal total;

    @Column(columnDefinition = "json")
    private String jsonItems; // lista de itens em JSON

    private boolean closed;

    public Order(Waiter waiter, LocalDateTime openedAt, boolean closed, Integer orderNumber) {
        this.waiter = waiter;
        this.openedAt = openedAt;
        this.closed = closed;
        this.orderNumber = orderNumber;
    }
    public Order() {
    }
    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getJsonItems() {
        return jsonItems;
    }

    public void setJsonItems(String jsonItems) {
        this.jsonItems = jsonItems;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}

