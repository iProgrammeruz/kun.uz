package com.codeuz.repository;

import com.codeuz.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.time.LocalDate;
import java.util.List;


public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {

    @Query(" from EmailHistoryEntity where email =:email order by createdDate")
    List<EmailHistoryEntity> findAll1(String email);


    @Query(value = " select * from email_history where created_date between :from and :to order by created_date", nativeQuery = true)
    List<EmailHistoryEntity> findAll2(LocalDate from, LocalDate to);


    @Query(" from EmailHistoryEntity order by createdDate")
    Page<EmailHistoryEntity> findAll(Pageable pageable);


}
