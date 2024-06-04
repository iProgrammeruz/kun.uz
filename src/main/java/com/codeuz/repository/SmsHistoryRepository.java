package com.codeuz.repository;

import com.codeuz.dto.SmsHistoryDTO;
import com.codeuz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer>, PagingAndSortingRepository<SmsHistoryEntity, Integer> {

    @Query(" from SmsHistoryEntity order by createdDate desc limit 1")
    Optional<SmsHistoryEntity> findByPhone(String phone);

    //Optional<EmailHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);

    List<SmsHistoryEntity> findAllByPhone(String phone);

    @Query(value = "select * from sms_historyEntity where created_date between :from and :to", nativeQuery = true)
    List<SmsHistoryEntity> findAllByGivenDate(LocalDate from, LocalDate to);
}
