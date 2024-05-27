package com.codeuz.repository;

import com.codeuz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {

    @Query(" from SmsHistoryEntity order by createdDate desc limit 1")
    Optional<SmsHistoryEntity> findByPhone(String phone);

    //Optional<EmailHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);

}
