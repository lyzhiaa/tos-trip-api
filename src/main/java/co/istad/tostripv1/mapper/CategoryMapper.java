package co.istad.tostripv1.mapper;

import co.istad.tostripv1.domain.Category;
import co.istad.tostripv1.feature.category.dto.CategoryCreateRequest;
import co.istad.tostripv1.feature.category.dto.CategoryResponse;
import co.istad.tostripv1.feature.category.dto.CategoryUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // create
    Category fromCategoryCreateRequest(CategoryCreateRequest categoryCreateRequest);
    // get by uuid
    CategoryResponse toCategoryResponse(Category category);
    // get all
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
    // update
    void fromCategoryUpdateRequest(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Category category);

}
