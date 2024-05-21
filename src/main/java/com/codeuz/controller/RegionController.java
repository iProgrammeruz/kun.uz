package com.codeuz.controller;


import com.codeuz.dto.RegionCreateDTO;
import com.codeuz.dto.RegionDTO;
import com.codeuz.enums.Languages;
import com.codeuz.mapper.RegionMapper;
import com.codeuz.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {


    @Autowired
    private RegionService regionService;


    @PostMapping("/create")
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionCreateDTO region) {
        RegionDTO response = regionService.create(region);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody RegionCreateDTO region) {
        regionService.update(id, region);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegionDTO>> all() {
        return ResponseEntity.ok(regionService.getAll());
    }

    /*@GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> byLanguage(@RequestParam("language") Languages language) {
        List<RegionDTO> regioList = regionService.getAllByLanguage(language);
        return ResponseEntity.ok(regioList);
    }*/

    @GetMapping("/lang")
    public ResponseEntity<List<RegionMapper>> getAllByLanguage(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Languages language) {
        return ResponseEntity.ok(regionService.getAllByLanguage(language));
    }






}
