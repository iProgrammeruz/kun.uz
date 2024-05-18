package com.codeuz.repository;

import com.codeuz.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    List<RegionEntity> findAllByLanguage(String language);




}
