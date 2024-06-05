package com.codeuz.util;

import com.codeuz.config.CustomUserDetail;
import com.codeuz.dto.auth.JwtDTO;
import com.codeuz.entity.ProfileEntity;
import com.codeuz.enums.ProfileRole;
import com.codeuz.exp.AppForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static JwtDTO getJwt(String token) {

        String jwt = token.substring(7);
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        return jwtDTO;
    }


    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwt(token);
        if(!dto.getRole().equals(requiredRole)){
            throw new AppForbiddenException("You do not have permission to access this method!");
        }
        return dto;
    }


    public static Integer getProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.getProfile().getId();
    }


    public static ProfileEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        return user.getProfile();
    }



}
