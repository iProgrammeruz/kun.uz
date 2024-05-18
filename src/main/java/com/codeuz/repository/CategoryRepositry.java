package com.codeuz.repository;

import com.codeuz.entity.CategoryEntity;
import com.codeuz.enums.Languages;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepositry extends CrudRepository<CategoryEntity, Integer> {


    List<CategoryEntity> findAllByLanguage(String language);



}
