package com.codeuz.controller;

import com.codeuz.dto.article.ArticleCreateDTO;
import com.codeuz.dto.article.ArticleDTO;
import com.codeuz.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private  ArticleService articleService;


    // Moderator
    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleCreateDTO article) {
        return ResponseEntity.ok(articleService.create(article));
    }

    //Moderator
    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") String id,
                                          @Valid @RequestBody ArticleCreateDTO article){
        articleService.update(id, article);
        return ResponseEntity.ok(true);
    }

    //Moderator
    @DeleteMapping("/moderator/delete/{id}")
    public Boolean delete(@PathVariable("id") String id){
        articleService.delete(id);
        return true;
    }


    //Publisher
    @PutMapping("/update_status/{id}")
    public ResponseEntity<ArticleDTO> updateStatus(@PathVariable("id") String id,
                                                @Valid @RequestBody ArticleCreateDTO article){
        ArticleDTO articleDTO = articleService.updateStatus(id, article);
        return ResponseEntity.ok().body(articleDTO);
    }



}
