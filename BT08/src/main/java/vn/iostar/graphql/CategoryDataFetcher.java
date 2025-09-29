package vn.iostar.graphql;

import java.util.List;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import vn.iostar.entity.Category;
import vn.iostar.service.CategoryService;

@DgsComponent
public class CategoryDataFetcher {

    private final CategoryService categoryService;

    public CategoryDataFetcher(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @DgsQuery
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @DgsQuery
    public Category category(@InputArgument Long id) {
        return categoryService.findById(id).orElse(null);
    }

    @DgsMutation
    public Category addCategory(@InputArgument String name,
                                @InputArgument String images) {
        Category c = new Category();
        c.setName(name);
        c.setImages(images);
        return categoryService.save(c);
    }

    @DgsMutation
    public Category updateCategory(@InputArgument Long id,
                                   @InputArgument String name,
                                   @InputArgument String images) {
        return categoryService.findById(id).map(c -> {
            if (name != null) c.setName(name);
            if (images != null) c.setImages(images);
            return categoryService.save(c);
        }).orElse(null);
    }

    @DgsMutation
    public Boolean deleteCategory(@InputArgument Long id) {
        return categoryService.delete(id);
    }
}
