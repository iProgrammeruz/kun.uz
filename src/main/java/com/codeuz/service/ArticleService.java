package com.codeuz.service;

import com.codeuz.dto.article.ArticleCreateDTO;
import com.codeuz.dto.article.ArticleDTO;
import com.codeuz.entity.ArticleEntity;
import com.codeuz.entity.CategoryEntity;
import com.codeuz.entity.ProfileEntity;
import com.codeuz.entity.RegionEntity;
import com.codeuz.enums.ArticleStatus;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.ArticleRepository;
import com.codeuz.repository.ArticleTypesRepository;
import com.codeuz.repository.CategoryRepositry;
import com.codeuz.repository.RegionRepository;
import com.codeuz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTypesRepository articleTypesRepository;
    @Autowired
    private ArticleTypesService articleTypesService;

    //Moderator
    public ArticleDTO create(ArticleCreateDTO article) {
        Integer moderatorId = SecurityUtil.getProfileId();

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(article.getTitle());
        entity.setDescription(article.getDescription());
        entity.setContent(article.getContent());
        entity.setImageId(article.getImageId());
        entity.setRegionId(article.getRegionId());
        entity.setCategoryId(article.getCategoryId());
        entity.setModeratorId(moderatorId);
        articleRepository.save(entity);
        articleTypesService.create(entity.getId(), article.getTypesList());
        return toDTO(entity);
    }

    //Moderator
    public ArticleDTO update(String articleId, ArticleCreateDTO dto) {
        ArticleEntity entity = get(articleId);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);
        articleTypesService.merge(articleId, dto.getTypesList());
        return toDTO(entity);
    }

    /*public Boolean update(String id, ArticleCreateDTO article) {
        Integer moderatorId = SecurityUtil.getProfileId();
        ArticleEntity articleEntity = get(id);
        articleEntity.setTitle(article.getTitle());
        articleEntity.setDescription(article.getDescription());
        articleEntity.setContent(article.getContent());
        articleEntity.setSharedCount(article.getSharedCount());
        articleEntity.setImageId(article.getImageId());     // remove old image
        articleEntity.setRegionId(article.getRegionId());
        articleEntity.setCategoryId(article.getCategoryId());
        articleEntity.setModeratorId(moderatorId);
        articleRepository.save(articleEntity);
        articleTypesService.merge(id, article.getTypes());
        return true;
    }*/


    //Moderator
    public Boolean delete(String id) {
        articleRepository.deleteById(id);
        return true;
    }

    //Publisher
    public ArticleDTO updateStatus(String id, ArticleCreateDTO article) {
        ArticleEntity articleEntity = get(id);
        articleEntity.setStatus(article.getStatus());
        articleRepository.save(articleEntity);
        return toDTO(articleEntity);
    }







    public ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegion().getId());
        dto.setCategoryId(entity.getCategory().getId());
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            throw new AppBadException("Article not found");
        });
    }


    /*public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Article with id " + id + " not found");
        });
    }*/

   /* public RegionEntity getRegion(Integer regionId) {
        return regionRepository.findById(regionId).orElseThrow(() -> {
            throw new IllegalArgumentException("Region not found");
        });
    }

    public CategoryEntity getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new IllegalArgumentException("Category not found");
        });
    }*/



}
