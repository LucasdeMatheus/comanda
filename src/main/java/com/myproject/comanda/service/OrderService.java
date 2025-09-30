package com.myproject.comanda.service;

import com.myproject.comanda.domain.comanda.Order;
import com.myproject.comanda.domain.comanda.OrderRepository;
import com.myproject.comanda.domain.item.Item;
import com.myproject.comanda.domain.item.ItemDTO;
import com.myproject.comanda.domain.product.Product;
import com.myproject.comanda.domain.product.ProductRepository;
import com.myproject.comanda.domain.waiter.WaiterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WaiterRepository waiterRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Order> openOrder(Integer orderNumber, String login) {
        try {
            Order order = new Order(
                    waiterRepository.findByLogin(login),
                    LocalDateTime.now(),
                    false,
                    orderNumber
            );
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Order> closeOrder(Integer orderNumber) {
        try {
            Order order = orderRepository.findByOrderNumberAndClosedIsFalse(orderNumber)
                    .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

            order.setClosedAt(LocalDateTime.now());
            order.setClosed(true);

            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Order>> getOrdersOfToday() {
        try {
            LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

            List<Order> orders = orderRepository.findAllByOpenedAtBetween(startOfDay, endOfDay);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Order> getByOrderNumber(Integer orderNumber) {
        try {
            Order order = orderRepository.findByOrderNumberAndClosedIsFalse(orderNumber)
                    .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Order> addItem(Integer orderNumber, ItemDTO itemDTO) {
        try {
            Order order = orderRepository.findByOrderNumberAndClosedIsFalse(orderNumber)
                    .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

            List<Item> itens;
            if (order.getJsonItems() != null && !order.getJsonItems().isEmpty()) {
                itens = objectMapper.readValue(order.getJsonItems(), new TypeReference<List<Item>>() {});
            } else {
                itens = new ArrayList<>();
            }

            long nextId = itens.stream()
                    .map(Item::getId)
                    .filter(id -> id != null)
                    .max(Long::compareTo)
                    .orElse(0L) + 1;

            Item item = new Item(itemDTO);
            item.setId(nextId);

            // Buscar o produto pelo ID informado no DTO
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            item.setProduct(product);

            itens.add(item);

            order.setJsonItems(objectMapper.writeValueAsString(itens));
            orderRepository.save(order);

            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    public ResponseEntity<Order> editItem(Integer orderNumber, Long itemId, Item updatedItem) {
        try {
            Order order = orderRepository.findByOrderNumberAndClosedIsFalse(orderNumber)
                    .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

            List<Item> items;
            if (order.getJsonItems() != null && !order.getJsonItems().isEmpty()) {
                items = objectMapper.readValue(order.getJsonItems(), new TypeReference<List<Item>>() {});
            } else {
                items = new ArrayList<>();
            }

            boolean encontrado = false;
            for (int i = 0; i < items.size(); i++) {
                Long currentId = items.get(i).getId();
                if (currentId != null && currentId.equals(itemId)) {
                    updatedItem.setId(currentId);
                    items.set(i, updatedItem);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                return ResponseEntity.notFound().build();
            }

            order.setJsonItems(objectMapper.writeValueAsString(items));
            orderRepository.save(order);

            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Order> removeItem(Integer orderNumber, Long itemId) {
        try {
            Order order = orderRepository.findByOrderNumberAndClosedIsFalse(orderNumber)
                    .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

            List<Item> itens;
            if (order.getJsonItems() != null && !order.getJsonItems().isEmpty()) {
                itens = objectMapper.readValue(order.getJsonItems(), new TypeReference<List<Item>>() {});
            } else {
                itens = new ArrayList<>();
            }

            boolean removed = itens.removeIf(i -> {
                Long id = i.getId();
                return id != null && id.equals(itemId);
            });

            if (!removed) {
                return ResponseEntity.notFound().build();
            }

            order.setJsonItems(objectMapper.writeValueAsString(itens));
            orderRepository.save(order);

            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
