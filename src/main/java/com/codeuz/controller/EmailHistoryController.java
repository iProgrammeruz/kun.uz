package com.codeuz.controller;

import com.codeuz.dto.EmailHistoryDTO;
import com.codeuz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/email")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;


    @GetMapping("/history_by_email")
    public ResponseEntity<List<EmailHistoryDTO>> getAllEmailHistoryByEmail(@RequestParam("email") String email) {
        List<EmailHistoryDTO> response = emailHistoryService.getAllEmailHistoryByEmail(email);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/history_by_given_date")
    public ResponseEntity<List<EmailHistoryDTO>> getAllEmailHistoryByCreatedDate(@RequestParam("given_date") LocalDate givenDate) {
        List<EmailHistoryDTO> response = emailHistoryService.getAllEmailHistoryByGivenDate(givenDate);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/history_by_pagination")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> getAllEmailHistoryByPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<EmailHistoryDTO> response = emailHistoryService.getAllEmailHistoryByPagination(page-1, size);
        return ResponseEntity.ok(response);
    }






}
