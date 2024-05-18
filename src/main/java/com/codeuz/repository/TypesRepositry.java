package com.codeuz.repository;

import com.codeuz.entity.TypesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TypesRepositry extends CrudRepository<TypesEntity, Integer>, PagingAndSortingRepository<TypesEntity, Integer> {


    List<TypesEntity> findAllByLanguage(String language);

}
