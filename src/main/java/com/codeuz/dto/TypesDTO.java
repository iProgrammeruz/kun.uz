package com.codeuz.dto;
import com.codeuz.enums.Languages;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;



@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypesDTO {

    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
}
