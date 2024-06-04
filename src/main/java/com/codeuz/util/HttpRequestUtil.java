package com.codeuz.util;

import com.codeuz.dto.auth.JwtDTO;
import com.codeuz.enums.ProfileRole;
import com.codeuz.exp.AppForbiddenException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {


    public static JwtDTO getJwtDTO(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id, role);
        return dto;
    }


    public static JwtDTO getJwtDTO(HttpServletRequest request, ProfileRole requiredRole) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id, role);

        if (!dto.getRole().equals(requiredRole)) {
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }





}
