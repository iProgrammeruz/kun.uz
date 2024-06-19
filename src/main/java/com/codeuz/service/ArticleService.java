package com.codeuz.service;

import com.codeuz.dto.AttachDTO;
import com.codeuz.dto.article.ArticleCreateDTO;
import com.codeuz.dto.article.ArticleDTO;
import com.codeuz.dto.article.ArticleResponseDTO;
import com.codeuz.entity.ArticleEntity;
import com.codeuz.entity.CategoryEntity;
import com.codeuz.entity.ProfileEntity;
import com.codeuz.entity.RegionEntity;
import com.codeuz.enums.ArticleStatus;
import com.codeuz.enums.Languages;
import com.codeuz.exp.AppBadException;
import com.codeuz.mapper.ArticleShortInfoMapper;
import com.codeuz.repository.ArticleRepository;
import com.codeuz.repository.ArticleTypesRepository;
import com.codeuz.repository.CategoryRepositry;
import com.codeuz.repository.RegionRepository;
import com.codeuz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTypesRepository articleTypesRepository;
    @Autowired
    private ArticleTypesService articleTypesService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RegionService regionService;

    //1. Moderator
    public ArticleResponseDTO create(ArticleCreateDTO article) {
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

    //2. Update by articleId - Moderator
    public ArticleResponseDTO update(String articleId, ArticleCreateDTO dto) {
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


    //3. Delete by articleId - Moderator
    public Boolean delete(String articleId) {
        Optional<ArticleEntity> article = articleRepository.findByIdAndVisibleTrue(articleId);
        article.ifPresent(articleEntity -> articleEntity.setVisible(false));
        return true;
    }


    //4. Update status by articleId - Publisher
    public ArticleResponseDTO updateStatus(String articleId) {
        ArticleEntity articleEntity = get(articleId);
        if(articleEntity.getStatus() == ArticleStatus.NOT_PUBLISHED) {
            articleEntity.setStatus(ArticleStatus.PUBLISHED);
        }else {
            articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        }
        articleRepository.save(articleEntity);
        return toDTO(articleEntity);
    }

    //5. Get last 5 articles by typesId with short info(id(uuid),title,description,image(id,url),published_date)
    public List<ArticleResponseDTO> getLast5ArticlesByTypesId(Integer typesId) {
        List<ArticleShortInfoMapper> mapperList = articleRepository.getByTypesId(typesId, 5);
        List<ArticleResponseDTO> dtoList = new LinkedList<>();
        for (ArticleShortInfoMapper mapper : mapperList) {
            dtoList.add(toDTO(mapper));
        }
        return dtoList;
    }


    //6. Get last 3 articles by typesId with short info
    public List<ArticleResponseDTO> getLast3ArticlesByTypesId(Integer typesId) {
        List<ArticleShortInfoMapper> mapperList = articleRepository.getByTypesId(typesId, 3);
        return mapperList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //7.
    public List<ArticleResponseDTO> getLast8Articles(List<String> articleIdList) {
        List<ArticleShortInfoMapper> mapperList = articleRepository.getLast8Articles(articleIdList);
        return mapperList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    //8.
    //    id(uuid),title,description,content,shared_count,image_id,
    //    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
    //    created_date,published_date,visible,view_count
    public ArticleResponseDTO getById(String articleId, Languages lang) {
        ArticleEntity articleEntity = get(articleId);
        if(articleEntity.getStatus() != ArticleStatus.PUBLISHED) {
            throw new AppBadException("Article not found!");
        }
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(articleEntity.getId());
        dto.setTitle(articleEntity.getTitle());
        dto.setDescription(articleEntity.getDescription());
        dto.setContent(articleEntity.getContent());
        dto.setSharedCount(articleEntity.getSharedCount());
        dto.setRegion(regionService.getRegion(articleEntity.getRegionId(), lang));
        dto.setCategory(categoryService.getCategory(articleEntity.getCategoryId(), lang));
        dto.setPublishedDate(articleEntity.getPublishedDate());

        dto.setImageId(articleEntity.getImageId());
        dto.setRegionId(articleEntity.getRegionId());

    }





    public ArticleResponseDTO toDTO(ArticleShortInfoMapper mapper) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setImage(attachService.getDTOWithURL(mapper.getImageId()));
        return dto;
    }


    public ArticleResponseDTO toDTO(ArticleEntity entity) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegion().getId());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setTypesList(articleTypesRepository.findAllTypesIdByArticleId(entity.getId()));
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException("Article not found"));
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
