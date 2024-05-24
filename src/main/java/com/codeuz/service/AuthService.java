package com.codeuz.service;


import com.codeuz.dto.ProfileDTO;
import com.codeuz.dto.auth.RegistrationDTO;
import com.codeuz.entity.ProfileEntity;
import com.codeuz.enums.ProfileRole;
import com.codeuz.enums.ProfileStatus;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.ProfileRepository;
import com.codeuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;


    // Registration
    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setCreateDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        sendToRegistration(entity.getId(), dto.getEmail());
        return "To complete your registration please verify your email!";

    }

    // Authorization
    public String authorizationVerification(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        emailHistoryService.isNotExpiredEmail(entity.getEmail());   // check for link expiration date
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration is not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }


    // Authorization link or code resend to email
    public String authorizeResend(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() && !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration is not completed");
        }
        emailHistoryService.checkEmailLimit(email);
        sendToRegistration(entity.getId(), email);
        return "To complete your registration please verify your email!";
    }


    // Login
    public ProfileDTO login(String email, String password) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(password))) {
            throw new AppBadException("Wrong password");
        }
        if (entity.getStatus() != ProfileStatus.ACTIVE) {
            throw new AppBadException("User is not active");
        }
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    // Send link or code for registration
    public void sendToRegistration(Integer profileId, String email) {
        String url = "http://localhost:8080/auth/verification/" + profileId;
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: green;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please click button below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        mailSenderService.send(email, "Complete registration", text);
        emailHistoryService.createEmailHistory(email, text);   // Email history save method
    }



}
