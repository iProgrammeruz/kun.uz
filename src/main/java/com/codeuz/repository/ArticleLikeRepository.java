package com.codeuz.repository;

import com.codeuz.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {
    @Query("select count(a) from  ArticleLikeEntity a where a.articleId =?1 and a.emotionStatus ='LIKE' ")
    Long getArticleLikeCount(String articleId);

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

}
