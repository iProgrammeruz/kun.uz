package com.codeuz.controller;

import com.codeuz.dto.profile.ProfileCreateDTO;
import com.codeuz.dto.profile.ProfileDTO;
import com.codeuz.dto.profile.ProfileFilterDTO;
import com.codeuz.dto.ProfileUpdateDTO;
import com.codeuz.dto.auth.JwtDTO;
import com.codeuz.enums.ProfileRole;
import com.codeuz.service.ProfileService;
import com.codeuz.util.HttpRequestUtil;
import com.codeuz.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
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


    // 1 - Create Profile (ADMIN)
    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO profile){
        ProfileDTO response = profileService.create(profile);
        return ResponseEntity.ok().body(response);
    }

    // 2 - Update profile (ADMIN)
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody ProfileDTO profile,
                                          @RequestHeader("Authorization") String token){
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        //???
        profileService.update(id, profile);
        return ResponseEntity.ok().body(true);
    }

    // 3 - Update Profile detail (ANY)
    @PutMapping("/current")
    public ResponseEntity<Boolean> updateUser(@RequestHeader("Authorization") String token,
                                              @Valid @RequestBody ProfileUpdateDTO profile){
        JwtDTO dto = SecurityUtil.getJwt(token);
        profileService.updateUser(dto.getId(), profile);
        return ResponseEntity.ok().body(true);
    }

    // (ADMIN)
    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        profileService.delete(id);
        return true;
    }

    // (ADMIN)
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
