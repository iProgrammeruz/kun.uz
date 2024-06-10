package com.codeuz.repository;

import com.codeuz.entity.ArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);

}
