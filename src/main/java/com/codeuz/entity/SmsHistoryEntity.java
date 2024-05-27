package com.codeuz.entity;

import com.codeuz.enums.SmsStatus;
import com.codeuz.enums.SmsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "code", columnDefinition = "text")
    private String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SmsType type = SmsType.STANDARD;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


}
