package com.codeuz.service;

import com.codeuz.entity.ArticleTypesEntity;
import com.codeuz.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypesService {

    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    public void create(String articleId, List<Integer> typesList){
        for (Integer typesId : typesList) {
            create(articleId, typesId);
        }
    }

    public void create(String articleId, Integer typesId){
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setArticleId(articleId);
        entity.setTypesId(typesId);
        articleTypesRepository.save(entity);
    }


}
