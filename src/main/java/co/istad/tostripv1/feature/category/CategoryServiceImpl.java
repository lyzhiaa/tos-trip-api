package co.istad.tostripv1.feature.category;

import co.istad.tostripv1.domain.Category;
import co.istad.tostripv1.domain.Place;
import co.istad.tostripv1.feature.Place.PlaceRepository;
import co.istad.tostripv1.feature.Place.dto.PlaceResponse;
import co.istad.tostripv1.feature.category.dto.CategoryCreateRequest;
import co.istad.tostripv1.feature.category.dto.CategoryResponse;
import co.istad.tostripv1.feature.category.dto.CategoryUpdateRequest;
import co.istad.tostripv1.mapper.CategoryMapper;
import co.istad.tostripv1.mapper.PlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PlaceRepository placeRepository;

    // create category
    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This category already exists");
        }
        Category category = categoryMapper.fromCategoryCreateRequest(categoryCreateRequest);
        category.setUuid(UUID.randomUUID().toString());
        category.setCreatedAt(String.valueOf(LocalDateTime.now()));
        category.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    // get category by uuid
    @Override
    public CategoryResponse getCategory(String uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Place place = new Place();
        category.setPlaces(placeRepository.findByCategoryUuid(uuid));

        return categoryMapper.toCategoryResponse(category);
    }


    // get all categories
    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryResponseList(categories);
    }

    // update category
    @Override
    public CategoryResponse updateCategory(String uuid, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryMapper.fromCategoryUpdateRequest(categoryUpdateRequest, category);
        category.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    // delete category
    @Override
    public void deleteCategory(String uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
    }
}
