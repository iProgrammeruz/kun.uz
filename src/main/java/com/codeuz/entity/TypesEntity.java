package com.codeuz.entity;

import com.codeuz.enums.Languages;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "types")
public class TypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "name_uz")
    @Enumerated(EnumType.STRING)
    private Languages nameUz;

    @Column(name = "name_ru")
    @Enumerated(EnumType.STRING)
    private Languages nameRu;

    @Column(name = "name_en")
    @Enumerated(EnumType.STRING)
    private Languages nameEn;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
