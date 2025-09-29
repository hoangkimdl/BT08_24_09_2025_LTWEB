package vn.iostar.service;

import vn.iostar.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    boolean delete(Long id);

    List<Product> findByCategory(Long categoryId);
    List<Product> findAllSortedByPrice();
}
