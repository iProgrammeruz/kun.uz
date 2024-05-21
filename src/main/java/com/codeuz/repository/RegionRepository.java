package com.codeuz.repository;

import com.codeuz.entity.RegionEntity;
import com.codeuz.mapper.RegionMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    // 1st example findAll
    List<RegionEntity> findAllByVisibleTrueOrderByOrderNumberDesc();

    // 2nd example findAll
    @Query("from RegionEntity where visible = true order by orderNumber desc")
    List<RegionEntity> findAllVisible();


    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "   END as name " +
            " from region order by order_number desc, visible = true; ", nativeQuery = true)
    List<RegionMapper> findAllByLanguage(@Param("lang") String lang);





}
