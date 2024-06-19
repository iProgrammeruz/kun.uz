package com.codeuz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleLikeRepository extends CrudRepository<Ar> {
    @Query("select count(a) from  ArticleLikeEntity a where a.articleId =?1 and a.status ='LIKE' ")
    Long getArticleLikeCount(String articleId);

}
