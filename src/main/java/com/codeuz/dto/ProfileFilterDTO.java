package com.codeuz.dto;

import com.codeuz.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterDTO {

    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDateTime createDateFrom;
    private LocalDateTime createDateTo;

}
