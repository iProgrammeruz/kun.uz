package com.codeuz.service;

import com.codeuz.dto.EmailHistoryDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    // Create email history
    public String createEmailHistory(String email, String text) {

        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(text);
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


    // Get all email history by given date
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


    public void checkEmailLimit(String email) { // 1 minute - 3 attempt
        // 23/05/2024 19:01:13
        // 23/05/2024 19:01:23
        // 23/05/2024 19:01:33

        // 23/05/2024 19:00:55 -- (current -1)
        // 23/05/2024 19:01:55 -- current

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >=3) {
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
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
