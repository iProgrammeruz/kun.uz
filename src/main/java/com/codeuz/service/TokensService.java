package com.codeuz.service;

import com.codeuz.entity.TokenEntity;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class TokensService {

    @Autowired
    private TokenRepository tokenRepository;


    public Boolean checkSmsToken(String token) {
        Optional<TokenEntity> entity = tokenRepository.findTopByTokenOrderByExpireDateDesc(token);
        if (entity.isEmpty()) {
            throw new AppBadException("Token not found!");
        }
        TokenEntity tokenEntity = entity.get();
        if (tokenEntity.getExpireDate().plusDays(30).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Token expired, update token first");
        }
        return true;
    }


    public void saveToken(String tokens) {
        TokenEntity token = new TokenEntity();
        token.setToken(tokens);
    }

}
