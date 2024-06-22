package com.codeuz.repository;

import com.codeuz.entity.ArticleEntity;
import com.codeuz.mapper.ArticleShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);

    /*//5.1 with native query
    @Query(value = "select * from article as a " +
            "inner join article_types as ats on ats.article_id = a.id " +
            "where types_id ?1 and visible = true and status = 'PUBLISHED' " +
            "order by created_date desc limit 5", nativeQuery = true)
    List<ArticleEntity> getLast5ArticlesByTypesIdNative(Integer typesId);*/


    /*//5.2 with HQL
    @Query(value = "select a from ArticleEntity as a " +
            "inner join a.articleTypes as ats " +
            "where ats.typesId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            "order by a.createdDate desc limit 5")
    List<ArticleEntity> getLast5ArticlesByTypesId(Integer typesId);*/


    //5.3 - 6.
    @Query (value = " select a.id, a.title, a.description, a.imageId, a.publishedDate " +
    " from ArticleEntity as a " +
    " inner join a.articleTypes as ats " +
    " where ats.typesId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
    " order by a.createdDate desc " +
    " limit ?2 ")
    List<ArticleShortInfoMapper> getByTypesId(Integer typeId, int limit);


    // 7. Get Last 8 Articles witch id not included in given id list.
    @Query(value = " select a.id, a.title, a.description, a.imageId, a.publishedDate " +
            " from ArticleEntity as a " +
            " where a.visible = true and a.status = 'PUBLISHED' " +
            " and a.id not in ?1 " +
            " order by a.createdDate desc " +
            " limit 8 ")
    List<ArticleShortInfoMapper> getLast8Articles(List<String> ids);


    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount = coalesce(viewCount, 0) +1 where id = ?1")
    void increaseViewCount(String articleId);
}
