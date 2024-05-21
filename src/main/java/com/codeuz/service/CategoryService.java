package com.codeuz.service;
import com.codeuz.dto.CategoryCreateDTO;
import com.codeuz.dto.CategoryDTO;
import com.codeuz.entity.CategoryEntity;
import com.codeuz.enums.Languages;
import com.codeuz.mapper.CategoryMapper;
import com.codeuz.repository.CategoryRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;



@Service
public class CategoryService {

    @Autowired
    private CategoryRepositry categoryRepositry;


    public CategoryDTO create(CategoryCreateDTO category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setOrderNumber(category.getOrderNumber());
        categoryEntity.setNameUz(category.getNameUz());
        categoryEntity.setNameRu(category.getNameRu());
        categoryEntity.setNameEn(category.getNameEn());
        categoryRepositry.save(categoryEntity);
        return toDTO(categoryEntity);
    }


    public Boolean update(Integer id, CategoryCreateDTO category) {
        CategoryEntity categoryEntity = get(id);
        categoryEntity.setOrderNumber(category.getOrderNumber());
        categoryEntity.setNameUz(category.getNameUz());
        categoryEntity.setNameRu(category.getNameRu());
        categoryEntity.setNameEn(category.getNameEn());
        categoryRepositry.save(categoryEntity);
        return true;
    }


    public Boolean delete(Integer id) {
        categoryRepositry.deleteById(id);
        return true;
    }


    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> categoryEntities = categoryRepositry.findAllOrderByOrderNumner();
        List<CategoryDTO> categoryList = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> {
            categoryList.add(toDTO(categoryEntity));
        });
        return categoryList;
    }


    public List<CategoryMapper> getAllByLanguage(Languages language) {
        List<CategoryMapper> categoryMapperList = categoryRepositry.findAll(language.name());
        return categoryMapperList;
    }


    public CategoryEntity get(Integer id) {
        return categoryRepositry.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Category not found");
        });
    }

    public CategoryDTO toDTO(CategoryEntity categoryEntity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(categoryEntity.getId());
        dto.setNameUz(categoryEntity.getNameUz());
        dto.setNameRu(categoryEntity.getNameRu());
        dto.setNameEn(categoryEntity.getNameEn());
        dto.setOrderNumber(categoryEntity.getOrderNumber());
        dto.setCreatedDate(categoryEntity.getCreatedDate());
        return dto;
    }


}
