package com.myproject.comanda.Controller;

import com.myproject.comanda.domain.product.Product;
import com.myproject.comanda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(
            @PathVariable("id") Long id
    ){
        return productService.getById(id);
    }
    @PostMapping
    public ResponseEntity<Product> create(
            @RequestBody Product product
    ){
        return productService.create(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @RequestBody Product product,
            @PathVariable("id") Long id){
        return productService.update(id, product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable("id") Long id){
        return productService.delete(id);
    }
}
