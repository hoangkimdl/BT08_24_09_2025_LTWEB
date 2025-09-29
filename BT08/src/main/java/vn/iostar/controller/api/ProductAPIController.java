package vn.iostar.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iostar.entity.Product;
import vn.iostar.entity.Category;
import vn.iostar.service.ProductService;
import vn.iostar.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Lấy tất cả product
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    // Lấy tất cả product theo price tăng dần
    @GetMapping("/sorted")
    public ResponseEntity<List<Product>> getAllSorted() {
        return ResponseEntity.ok(productService.findAllSortedByPrice());
    }

    // Lấy tất cả product của 1 category
    @GetMapping("/by-category")
    public ResponseEntity<List<Product>> getByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    // Thêm product
    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestParam String title,
                                       @RequestParam Integer quantity,
                                       @RequestParam Double price,
                                       @RequestParam String description,
                                       @RequestParam Long categoryId) {
        Product p = new Product();
        p.setTitle(title);
        p.setQuantity(quantity);
        p.setPrice(price);
        p.setDescription(description);

        Optional<Category> cat = categoryService.findById(categoryId);
        cat.ifPresent(p::setCategory);

        return ResponseEntity.ok(productService.save(p));
    }

    // Cập nhật product
    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestParam Long id,
                                          @RequestParam String title,
                                          @RequestParam Integer quantity,
                                          @RequestParam Double price,
                                          @RequestParam String description,
                                          @RequestParam Long categoryId) {
        Optional<Product> opt = productService.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Product p = opt.get();
        p.setTitle(title);
        p.setQuantity(quantity);
        p.setPrice(price);
        p.setDescription(description);

        Optional<Category> cat = categoryService.findById(categoryId);
        cat.ifPresent(p::setCategory);

        return ResponseEntity.ok(productService.save(p));
    }

    // Xoá product
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Optional<Product> opt = productService.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
