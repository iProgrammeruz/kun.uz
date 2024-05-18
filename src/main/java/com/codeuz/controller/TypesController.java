package com.codeuz.controller;


import com.codeuz.dto.CategoryDTO;
import com.codeuz.dto.TypesDTO;
import com.codeuz.enums.Languages;
import com.codeuz.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;


    @PostMapping("/create")
    public ResponseEntity<TypesDTO> create(@RequestBody TypesDTO typesDTO) {
        TypesDTO response = typesService.create(typesDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id, @RequestBody TypesDTO typesDTO) {
        typesService.update(id, typesDTO);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        typesService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<TypesDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<TypesDTO> typesList = typesService.getAllWithPanigation(page - 1, size);
        return ResponseEntity.ok(typesList);
    }

    @GetMapping("/by_lang")
    public ResponseEntity<List<TypesDTO>> byLanguage(@RequestParam Languages language){
        List<TypesDTO> typesList = typesService.getByLanguage(language);
        return ResponseEntity.ok(typesList);
    }




}
