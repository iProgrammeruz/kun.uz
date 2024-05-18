package com.codeuz.service;

import com.codeuz.dto.CategoryDTO;
import com.codeuz.entity.CategoryEntity;
import com.codeuz.enums.Languages;
import com.codeuz.repository.CategoryRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepositry categoryRepositry;


    public CategoryDTO create(CategoryDTO categoryDTO) {
        CategoryEntity category = new CategoryEntity();
        category.setOrderNumber(categoryDTO.getOrderNumber());
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setNameEn(categoryDTO.getNameEn());
        category.setCreatedDate(categoryDTO.getCreatedDate());
        category.setVisible(categoryDTO.getVisible());
        categoryRepositry.save(category);
        categoryDTO.setId(category.getId());
        return categoryDTO;
    }


    public Boolean update(Integer id, CategoryDTO categoryDTO) {
        CategoryEntity category = get(id);
        category.setOrderNumber(categoryDTO.getOrderNumber());
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setNameEn(categoryDTO.getNameEn());
        category.setCreatedDate(categoryDTO.getCreatedDate());
        category.setVisible(categoryDTO.getVisible());
        categoryRepositry.save(category);
        categoryDTO.setId(category.getId());
        return true;
    }


    public Boolean delete(Integer id) {
        CategoryEntity category = get(id);
        categoryRepositry.delete(category);
        return true;
    }


    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> categoryEntities = categoryRepositry.findAll();
        List<CategoryDTO> categoryList = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(categoryEntity.getId());
            categoryDTO.setOrderNumber(categoryEntity.getOrderNumber());
            categoryDTO.setNameUz(categoryEntity.getNameUz());
            categoryDTO.setNameRu(categoryEntity.getNameRu());
            categoryDTO.setNameEn(categoryEntity.getNameEn());
            categoryDTO.setCreatedDate(categoryEntity.getCreatedDate());
            categoryDTO.setVisible(categoryEntity.getVisible());
            categoryList.add(categoryDTO);
        });
        return categoryList;
    }


    public List<CategoryDTO> getByLanguage(Languages language) {
        List<CategoryEntity> categoryEntities = categoryRepositry.findAllByLanguage(language.name());
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(categoryEntity.getId());
            categoryDTO.setOrderNumber(categoryEntity.getOrderNumber());
            categoryDTO.setNameUz(categoryEntity.getNameUz());
            categoryDTO.setNameRu(categoryEntity.getNameRu());
            categoryDTO.setNameEn(categoryEntity.getNameEn());
            categoryDTO.setCreatedDate(categoryEntity.getCreatedDate());
            categoryDTO.setVisible(categoryEntity.getVisible());
            categoryDTOList.add(categoryDTO);
        });
        return categoryDTOList;
    }












    public CategoryEntity get(Integer id) {
        return categoryRepositry.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Category not found");
        });
    }



}
