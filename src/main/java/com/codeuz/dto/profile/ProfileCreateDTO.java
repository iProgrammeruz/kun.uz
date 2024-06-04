package com.codeuz.dto.profile;


import com.codeuz.enums.ProfileRole;
import com.codeuz.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileCreateDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 20, message = "Name should be more than 2 characters")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Size(min = 2, max = 20, message = "Surname should be more than 2 characters")
    private String surname;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Size(min = 12, max = 12, message = "Phone number shoudld have 12 characters")
    private String phone;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "Password must contain at least one letter and one number")
    private String password;

    private ProfileStatus status;

    private ProfileRole role;

}
