package co.istad.tostripv1.feature.category;

import co.istad.tostripv1.feature.category.dto.CategoryCreateRequest;
import co.istad.tostripv1.feature.category.dto.CategoryResponse;
import co.istad.tostripv1.feature.category.dto.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    // create category
    CategoryResponse createCategory(CategoryCreateRequest request);
    // get category by id
    CategoryResponse getCategory(String uuid);

    List<CategoryResponse> getCategories();
    // update category

    CategoryResponse updateCategory(String uuid, CategoryUpdateRequest categoryUpdateRequest);

    void deleteCategory(String uuid);

}
