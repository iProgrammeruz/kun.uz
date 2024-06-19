package com.codeuz.repository;

import com.codeuz.entity.ArticleEntity;
import com.codeuz.entity.ArticleTypesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Integer> {


    @Modifying
    @Transactional
    void deleteAllByArticleId(String articleId);

    @Query("Select a.typesId from ArticleTypesEntity as a where a.articleId =?1 ")
    List<Integer> findAllTypesIdByArticleId(String articleId);

    @Modifying
    @Transactional
    @Query("delete from ArticleTypesEntity  where articleId =?1 and typesId =?2")
    void deleteByArticleIdAndTypesId(String articleId, Integer typesId);

    //List<ArticleEntity> FindTopByArticleIdTypesIdOrderByArticleIdDesc(int[] typesId);

}
