package com.codeuz.repository;

import com.codeuz.entity.TypesEntity;
import com.codeuz.mapper.TypesMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypesRepositry extends CrudRepository<TypesEntity, Integer>, PagingAndSortingRepository<TypesEntity, Integer> {


    @Query(value = "select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'RU' THEN name_ru " +
            "   WHEN 'EN' THEN name_en " +
            "   END as name " +
            " from types order by order_number desc, visible = true; ", nativeQuery = true)
    List<TypesMapper> findAllByLanguage(@Param("lang") String lang);









}
