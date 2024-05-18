package com.codeuz.dto;


import com.codeuz.enums.Languages;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TypesDTO {

    private Integer id;
    private String orderNumber;
    private Languages nameUz;
    private Languages nameRu;
    private Languages nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
}
