package vn.iostar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Lấy tất cả product sắp xếp theo price tăng dần
    List<Product> findAllByOrderByPriceAsc();

    // Lấy tất cả product theo categoryId
    List<Product> findByCategory_Id(Long categoryId);
}
