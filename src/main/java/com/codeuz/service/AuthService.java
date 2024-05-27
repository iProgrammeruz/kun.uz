package com.codeuz.service;
import com.codeuz.dto.ProfileDTO;
import com.codeuz.dto.auth.RegistrationDTO;
import com.codeuz.entity.ProfileEntity;
import com.codeuz.entity.SmsHistoryEntity;
import com.codeuz.enums.ProfileRole;
import com.codeuz.enums.ProfileStatus;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.ProfileRepository;
import com.codeuz.repository.SmsHistoryRepository;
import com.codeuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;


    // Registration with email
    public String registrationWithEmail(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        sendToRegistration(entity.getId(), dto.getEmail());
        return "To complete your registration please verify your email!";
    }


    // Registration with phone
    public String registrationWithPhone(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            throw new AppBadException("Phone already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        smsService.sendSms(dto.getPhone());
        return "To complete your registration please verify your Phone number!";
    }


    // Authorization with email
    public String authorizationWithEmail(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        emailHistoryService.isNotExpiredEmail(entity.getEmail());   // check for link expiration date
        if (!entity.getVisible() && !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration is not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }


    // Authorization with phone
    public String authorizationWithPhone(String code, String phone) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (profileEntity.isEmpty()) {
            throw new AppBadException(" Profile not found ");
        }
        ProfileEntity profile = profileEntity.get();
        if (!profile.getVisible() && !profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException(" Registration is not completed ");
        }
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByPhone(phone);
        if (optional.isEmpty()) {
            throw new AppBadException(" This phone is not find ");
        }
        SmsHistoryEntity sms = optional.get();
        if (!sms.getCode().equals(code)) {
            throw new AppBadException(" This code is not valid ");
        }
        smsService.isNotExpiredSms(phone);  // check for sms code expiration date
        profileRepository.updateStatus(profile.getId(), ProfileStatus.ACTIVE);
        return "Successfully verified your registration with phone number!";
    }


    // Authorization link or code resend to email
    public String authorizeResendEmail(String email) {
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


    //Authorization code resend to phone
    public String authorizeResendPhone(String phone) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (profileEntity.isEmpty()) {
            throw new AppBadException(" Profile not found ");
        }
        ProfileEntity profile = profileEntity.get();
        if (!profile.getVisible() && !profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException(" Registration is not completed ");
        }
        smsService.checkSmsLimit(phone);
        smsService.sendSms(phone);
        return "To complete your registration please verify your phone!";
    }


    // Login with email
    public ProfileDTO loginWithEmail(String email, String password) {
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
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    // Login with phone
    public ProfileDTO loginWithPhone(String phone, String password) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
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
        dto.setPhone(entity.getPhone());
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
