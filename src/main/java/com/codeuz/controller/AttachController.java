package com.codeuz.controller;

import com.codeuz.dto.AttachDTO;
import com.codeuz.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload1(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(fileName);
    }


    @PostMapping("/uploa2")
    public ResponseEntity<AttachDTO> upload2(@RequestParam("file") MultipartFile file) {
        AttachDTO response = attachService.saveAttach(file);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(value = "/open1/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open1(@PathVariable("fileName") String fileName) {
        return this.attachService.loadImage(fileName);
    }

    @GetMapping(value = "/open2/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open2(@PathVariable("fileName") String fileName) {
        return this.attachService.load(fileName);
    }


}
