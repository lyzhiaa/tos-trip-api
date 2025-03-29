package co.istad.tostripv1.feature.category;

import co.istad.tostripv1.feature.category.dto.CategoryCreateRequest;
import co.istad.tostripv1.feature.category.dto.CategoryResponse;
import co.istad.tostripv1.feature.category.dto.CategoryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // create category
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CategoryResponse createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        return categoryService.createCategory(categoryCreateRequest);
    }

    // get all categories
    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    // get category by uuid
    @GetMapping("/{uuid}")
    CategoryResponse getCategoryByUuid(@Valid @PathVariable("uuid") String uuid) {
        return categoryService.getCategory(uuid);
    }

    // update category
    @PatchMapping("/{uuid}")
    CategoryResponse updateCategory(@PathVariable("uuid") String uuid, @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return categoryService.updateCategory(uuid, categoryUpdateRequest);
    }

    // delete category
    @DeleteMapping("/{uuid}")
    void deleteCategory(@PathVariable("uuid") String uuid) {
        categoryService.deleteCategory(uuid);
    }

}
