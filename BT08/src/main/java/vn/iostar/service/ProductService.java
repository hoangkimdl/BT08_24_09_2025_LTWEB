package vn.iostar.service;

import vn.iostar.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllSortedByPrice();
    List<Product> findByCategoryId(Long categoryId);
    Optional<Product> findById(Long id);
    Product save(Product product);
    void delete(Long id);
}
