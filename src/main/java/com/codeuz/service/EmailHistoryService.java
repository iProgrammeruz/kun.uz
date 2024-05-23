package com.codeuz.service;

import com.codeuz.dto.EmailHistoryDTO;
import com.codeuz.dto.auth.RegistrationDTO;
import com.codeuz.entity.EmailHistoryEntity;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    // Create email history
    public String createEmailHistory(String url, String email) {

        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(url);
        emailHistoryRepository.save(entity);
        return "Email history saved";
    }


    // Get all email history by email
    public List<EmailHistoryDTO> getAllEmailHistoryByEmail(String email) {
        List<EmailHistoryEntity> emailList = emailHistoryRepository.findAll1(email);
        if (emailList == null) {
            throw new AppBadException("Email history not found or something went wrong");
        }
        List<EmailHistoryDTO> list = new ArrayList<>();
        emailList.forEach(emailHistoryEntity -> {
            list.add(toDto(emailHistoryEntity));
        });
        return list;
    }


    // Get all email history by created date
    public List<EmailHistoryDTO> getAllEmailHistoryByGivenDate(LocalDate givenDate) {
        LocalDate from = givenDate;
        LocalDate to = givenDate.plusDays(1);
        List<EmailHistoryEntity> emailList = emailHistoryRepository.findAll2(from, to);
        if (emailList == null) {
            throw new AppBadException("Email history not found or something went wrong");
        }
        List<EmailHistoryDTO> list = new ArrayList<>();
        emailList.forEach(emailHistoryEntity -> {
            list.add(toDto(emailHistoryEntity));
        });
        return list;
    }


    // Get all email history by pagination
    public PageImpl<EmailHistoryDTO> getAllEmailHistoryByPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistoryEntity> emailList = emailHistoryRepository.findAll(pageable);
        List<EmailHistoryDTO> list = new ArrayList<>();
        emailList.forEach(emailHistoryEntity -> {
            toDto(emailHistoryEntity);
            list.add(toDto(emailHistoryEntity));
        });
        return new PageImpl<>(list, pageable, emailList.getTotalElements());
    }


    // ToDTO method
    public EmailHistoryDTO toDto(EmailHistoryEntity emailHistoryEntity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(emailHistoryEntity.getId());
        dto.setMessage(emailHistoryEntity.getMessage());
        dto.setEmail(emailHistoryEntity.getEmail());
        dto.setCreatedDate(emailHistoryEntity.getCreatedDate());
        return dto;
    }



}
