package com.codeuz.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {

    @NotBlank
    private Integer id;

    @NotBlank
    private String message;

    @NotBlank
    private String email;

    @NotBlank
    private LocalDateTime createdDate = LocalDateTime.now();

}
