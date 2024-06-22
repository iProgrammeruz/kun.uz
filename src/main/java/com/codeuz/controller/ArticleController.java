package com.codeuz.controller;

import com.codeuz.dto.article.ArticleCreateDTO;
import com.codeuz.dto.article.ArticleResponseDTO;
import com.codeuz.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private  ArticleService articleService;


    // Moderator
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator")
    public ResponseEntity<ArticleResponseDTO> create(@Valid @RequestBody ArticleCreateDTO article) {
        return ResponseEntity.ok(articleService.create(article));
    }


    //Moderator
    @PreAuthorize("hasAnyRole('MODERATOR', 'PUBLISHER')")
    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<ArticleResponseDTO> update(@PathVariable("id") String id,
                                                     @Valid @RequestBody ArticleCreateDTO article){
        ArticleResponseDTO articleDTO = articleService.update(id, article);
        return ResponseEntity.ok().body(articleDTO);
    }


    //Moderator
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/moderator/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id){
        articleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //Publisher
    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/update_status/{id}")
    public ResponseEntity<ArticleResponseDTO> updateStatus(@PathVariable("id") String articleId){
        ArticleResponseDTO articleDTO = articleService.updateStatus(articleId);
        return ResponseEntity.ok().body(articleDTO);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/get_last5")
    public ResponseEntity<List<ArticleResponseDTO>> getLast5Articles(Integer typesId){
        List<ArticleResponseDTO> articleDTO = articleService.getLast5ArticlesByTypesId(typesId);
        return ResponseEntity.ok().body(articleDTO);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/get_last3")
    public ResponseEntity<List<ArticleResponseDTO>> getLast3Articles(Integer typesId){
        List<ArticleResponseDTO> articleDTO = articleService.getLast3ArticlesByTypesId(typesId);
        return ResponseEntity.ok().body(articleDTO);
    }


    @PreAuthorize("permitAll()")
    @PostMapping("/get_last8")
    public ResponseEntity<List<ArticleResponseDTO>> getLast8Articles(@RequestBody List<String> articleIdList) {
        return ResponseEntity.ok(articleService.getLast8Articles(articleIdList));
    }





}
