package com.codeuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionCreateDTO {

    @NotNull(message = "Order number required")
    private Integer orderNumber;
    @NotBlank(message = "NameUz required")
    private String nameUz;
    @NotBlank(message = "NameRu required")
    private String nameRu;
    @NotBlank(message = "NameEn required")
    private String nameEn;


}
