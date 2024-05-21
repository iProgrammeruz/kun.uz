package com.codeuz.controller;

import com.codeuz.dto.CategoryCreateDTO;
import com.codeuz.dto.CategoryDTO;
import com.codeuz.enums.Languages;
import com.codeuz.mapper.CategoryMapper;
import com.codeuz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateDTO category) {
        CategoryDTO response = categoryService.create(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody CategoryCreateDTO category) {
        categoryService.update(id, category);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> all() {
        List<CategoryDTO> categoryList = categoryService.getAll();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/by_lang/language")
    public ResponseEntity<List<CategoryMapper>> getAllByLanguage(@RequestParam Languages language) {
        return ResponseEntity.ok(categoryService.getAllByLanguage(language));
    }


}
