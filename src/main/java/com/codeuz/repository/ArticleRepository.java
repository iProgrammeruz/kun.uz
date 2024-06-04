package com.codeuz.repository;

import com.codeuz.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;



public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
}
