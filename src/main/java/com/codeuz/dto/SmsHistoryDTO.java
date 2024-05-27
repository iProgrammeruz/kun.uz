package com.codeuz.dto;


import com.codeuz.enums.SmsStatus;
import com.codeuz.enums.SmsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {

    private Integer id;

    @NotBlank
    private String phone;

    @NotBlank
    private String message;

    @NotBlank
    private SmsStatus status;

    @NotBlank
    private SmsType type = SmsType.STANDARD;

    @NotBlank
    private LocalDateTime createdDate = LocalDateTime.now();


}
