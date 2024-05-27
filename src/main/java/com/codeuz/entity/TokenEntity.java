package com.codeuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sms_token", columnDefinition = "text")
    private String token;

    @Column(name = "expire_date")
    private LocalDateTime expireDate = LocalDateTime.now();


}
