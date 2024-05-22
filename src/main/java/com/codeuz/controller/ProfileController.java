package com.codeuz.controller;

import com.codeuz.dto.ProfileCreateDTO;
import com.codeuz.dto.ProfileDTO;
import com.codeuz.dto.ProfileFilterDTO;
import com.codeuz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/profile")
public class ProfileController {


    @Autowired
    private ProfileService profileService;


    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO profile){
        ProfileDTO response = profileService.create(profile);
        return ResponseEntity.ok().body(response);
    }

    //admin
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody ProfileCreateDTO profile){
        profileService.update(id, profile);
        return ResponseEntity.ok().body(true);
    }

    //user
    @PutMapping("/update_user/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("id") Integer id, @Valid @RequestBody ProfileCreateDTO profile){
        profileService.updateUser(id, profile);
        return ResponseEntity.ok().body(true);
    }


    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        profileService.delete(id);
        return true;
    }

    @GetMapping("/all_with_pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> getAllWithPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size){
        PageImpl<ProfileDTO> pageList = profileService.getAllWithPagination(page - 1, size);
        return ResponseEntity.ok().body(pageList);
    }


    @PostMapping("/filter")
    public ResponseEntity<Page<ProfileDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestBody ProfileFilterDTO profileFilterDTO){
        PageImpl<ProfileDTO> pageList = profileService.filter(profileFilterDTO, page-1, size);
        return ResponseEntity.ok().body(pageList);
    }

}
