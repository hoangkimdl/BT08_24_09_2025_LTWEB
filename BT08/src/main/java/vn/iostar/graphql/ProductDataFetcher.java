package vn.iostar.graphql;

import java.util.List;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import vn.iostar.entity.Product;
import vn.iostar.service.ProductService;
import vn.iostar.service.UserService;
import vn.iostar.service.CategoryService;

@DgsComponent 
public class ProductDataFetcher {

    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductDataFetcher(ProductService productService,
                              UserService userService,
                              CategoryService categoryService) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @DgsQuery
    public List<Product> products() {
        return productService.findAll();
    }

    @DgsQuery
    public Product product(@InputArgument Long id) {
        return productService.findById(id).orElse(null);
    }

    @DgsQuery
    public List<Product> productsByCategory(@InputArgument Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @DgsQuery
    public List<Product> productsSortedByPrice() {
        return productService.findAllSortedByPrice();
    }

    @DgsMutation
    public Product addProduct(@InputArgument String title,
                              @InputArgument Integer quantity,
                              @InputArgument String desc,
                              @InputArgument Double price,
                              @InputArgument Long userId) {
        Product p = new Product();
        p.setTitle(title);
        p.setQuantity(quantity);
        p.setDesc(desc);
        p.setPrice(price);

        userService.findById(userId).ifPresent(p::setUser);
        return productService.save(p);
    }

    @DgsMutation
    public Product updateProduct(@InputArgument Long id,
                                 @InputArgument String title,
                                 @InputArgument Integer quantity,
                                 @InputArgument String desc,
                                 @InputArgument Double price,
                                 @InputArgument Long userId) {
        return productService.findById(id).map(p -> {
            if (title != null) p.setTitle(title);
            if (quantity != null) p.setQuantity(quantity);
            if (desc != null) p.setDesc(desc);
            if (price != null) p.setPrice(price);
            if (userId != null) {
                userService.findById(userId).ifPresent(p::setUser);
            }
            return productService.save(p);
        }).orElse(null);
    }

    @DgsMutation
    public Boolean deleteProduct(@InputArgument Long id) {
        return productService.delete(id);
    }
}
