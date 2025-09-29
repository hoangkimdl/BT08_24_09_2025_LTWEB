package vn.iostar.service.impl;

import org.springframework.stereotype.Service;
import vn.iostar.entity.Product;
import vn.iostar.repository.ProductRepository;
import vn.iostar.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public boolean delete(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        return productRepo.findByCategories_Id(categoryId);
    }

    @Override
    public List<Product> findAllSortedByPrice() {
        return productRepo.findAllByOrderByPriceAsc();
    }
}
