package com.codeuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


}
