package com.codeuz.service;

import com.codeuz.dto.article.ArticleCreateDTO;
import com.codeuz.dto.article.ArticleDTO;
import com.codeuz.entity.ArticleEntity;
import com.codeuz.entity.CategoryEntity;
import com.codeuz.entity.RegionEntity;
import com.codeuz.repository.ArticleRepository;
import com.codeuz.repository.CategoryRepositry;
import com.codeuz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CategoryRepositry categoryRepository;


    //Moderator
    public ArticleDTO create(ArticleCreateDTO article) {
        RegionEntity region = getRegion(article.getRegionId());
        CategoryEntity category = getCategory(article.getCategoryId());
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(article.getTitle());
        entity.setDescription(article.getDescription());
        entity.setContent(article.getContent());
        entity.setImageId(article.getImageId());
        entity.setRegion(region);
        entity.setCategory(category);
        //article types
        articleRepository.save(entity);
        return toDTO(entity);
    }

    //Moderator
    public Boolean update(String id, ArticleCreateDTO article) {
        RegionEntity region = getRegion(article.getRegionId());
        CategoryEntity category = getCategory(article.getCategoryId());
        ArticleEntity articleEntity = get(id);
        articleEntity.setTitle(article.getTitle());
        articleEntity.setDescription(article.getDescription());
        articleEntity.setContent(article.getContent());
        articleEntity.setSharedCount(article.getSharedCount());
        articleEntity.setImageId(article.getImageId());     // remove old image
        articleEntity.setRegion(region);
        articleEntity.setCategory(category);
        articleRepository.save(articleEntity);
        return true;
    }

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
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Article with id " + id + " not found");
        });
    }

    public RegionEntity getRegion(Integer regionId) {
        return regionRepository.findById(regionId).orElseThrow(() -> {
            throw new IllegalArgumentException("Region not found");
        });
    }

    public CategoryEntity getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new IllegalArgumentException("Category not found");
        });
    }



}
