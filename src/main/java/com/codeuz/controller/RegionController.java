package com.codeuz.controller;


import com.codeuz.dto.RegionDTO;
import com.codeuz.enums.Languages;
import com.codeuz.service.RegionService;
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
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO regionDTO) {
        RegionDTO response = regionService.create(regionDTO);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @RequestBody RegionDTO regionDTO) {
        regionService.update(id, regionDTO);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegionDTO>> list() {
        List<RegionDTO> regionList = regionService.getAll();
        return ResponseEntity.ok(regionList);
    }

    @GetMapping("/by_lang")
    public ResponseEntity<List<RegionDTO>> byLanguage(@RequestParam("language") Languages language) {
        List<RegionDTO> regioList = regionService.getByLanguage(language);
        return ResponseEntity.ok(regioList);
    }




}
