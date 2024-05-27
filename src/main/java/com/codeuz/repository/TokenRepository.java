package com.codeuz.repository;

import com.codeuz.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findTopByTokenOrderByExpireDateDesc(String token);

}
