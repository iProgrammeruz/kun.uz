package com.codeuz.repository;

import com.codeuz.entity.CategoryEntity;
import com.codeuz.enums.Languages;
import com.codeuz.mapper.CategoryMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepositry extends CrudRepository<CategoryEntity, Integer> {

    @Query(value = "select * from category", nativeQuery = true)
    List<CategoryEntity> findAllByLanguage(Languages language);

    @Query(value = " from CategoryEntity order by orderNumber ")
    List<CategoryEntity> findAllOrderByOrderNumner();

    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "   END as name " +
            " from regions order by order_number desc, visible = true; ", nativeQuery = true)
    List<CategoryMapper> findAll(@Param("lang") String lang);
}
