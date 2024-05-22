package com.codeuz.repository;

import com.codeuz.entity.ProfileEntity;
import com.codeuz.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);


    @Transactional
    @Modifying
    @Query(" update ProfileEntity set status = ?2 where id = ?1 ")
    int updateStatus(Integer id, ProfileStatus status);

}
