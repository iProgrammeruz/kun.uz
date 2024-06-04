package com.codeuz.controller;


import com.codeuz.dto.EmailHistoryDTO;
import com.codeuz.dto.SmsHistoryDTO;
import com.codeuz.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/history_by_phone")
    public ResponseEntity<List<SmsHistoryDTO>> getAllSmsByPhone(@RequestParam("phone") String phone) {
        List<SmsHistoryDTO> smsHistoryDTOS = smsService.getAllSmsByPhone(phone);
        return ResponseEntity.ok(smsHistoryDTOS);
    }


    @GetMapping("/history_by_givenDate")
    public ResponseEntity<List<SmsHistoryDTO>> getAllSmsByGivenDate(@RequestParam("givenDate") LocalDate givenDate) {
        List<SmsHistoryDTO> response = smsService.getAllSmsByGivenDate(givenDate);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/history_by_pagination")
    public ResponseEntity<PageImpl<SmsHistoryDTO>> getAllEmailHistoryByPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<SmsHistoryDTO> response = smsService.getAllEmailHistoryByPagination(page-1, size);
        return ResponseEntity.ok(response);
    }




}
