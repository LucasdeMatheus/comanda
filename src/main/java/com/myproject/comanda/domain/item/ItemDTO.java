package com.myproject.comanda.domain.item;


import java.math.BigDecimal;
import java.util.List;

public record ItemDTO(
        Long productId,
        Integer quantity,
        String observation,
        List<String>additional
) {
}
