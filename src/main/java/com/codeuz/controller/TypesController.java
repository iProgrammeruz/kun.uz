package com.codeuz.controller;


import com.codeuz.dto.TypesCreateDTO;
import com.codeuz.dto.TypesDTO;
import com.codeuz.enums.Languages;
import com.codeuz.mapper.TypesMapper;
import com.codeuz.service.TypesService;
import jakarta.validation.Valid;
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
    public ResponseEntity<TypesDTO> create(@Valid @RequestBody TypesCreateDTO types) {
        TypesDTO response = typesService.create(types);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id, @Valid @RequestBody TypesCreateDTO types) {
        typesService.update(id, types);
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

    @GetMapping("/lang")
    public ResponseEntity<List<TypesMapper>> getAllByLanguage(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Languages language) {
        return ResponseEntity.ok(typesService.getAllByLanguage(language));
    }


}
