package vn.iostar.graphql;

import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import vn.iostar.entity.Category;
import vn.iostar.service.CategoryService;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class CategoryDataFetcher {

    @Autowired
    private CategoryService categoryService;

    // Query: Lấy tất cả Category
    @DgsQuery
    public List<Category> categories() {
        return categoryService.findAll();
    }

    // Query: Lấy 1 Category theo ID
    @DgsQuery
    public Category category(@InputArgument Long id) {
        return categoryService.findById(id).orElse(null);
    }

    // Mutation: Thêm Category
    @DgsMutation
    public Category addCategory(@InputArgument String name,
                                @InputArgument String description,
                                @InputArgument String images) {
        Category c = new Category();
        c.setName(name);
        c.setDescription(description);
        c.setImages(images);
        return categoryService.save(c);
    }

    // Mutation: Cập nhật Category
    @DgsMutation
    public Category updateCategory(@InputArgument Long id,
                                   @InputArgument String name,
                                   @InputArgument String description,
                                   @InputArgument String images) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) return null;

        Category c = opt.get();
        if (name != null) c.setName(name);
        if (description != null) c.setDescription(description);
        if (images != null) c.setImages(images);

        return categoryService.save(c);
    }

    // Mutation: Xoá Category
    @DgsMutation
    public Boolean deleteCategory(@InputArgument Long id) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) return false;

        categoryService.delete(id);
        return true;
    }
}
