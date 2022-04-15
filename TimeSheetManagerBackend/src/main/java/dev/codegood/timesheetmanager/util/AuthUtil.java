package dev.codegood.timesheetmanager.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthUtil {
    public static String getUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (String) jwt.getClaims().get("uid");
    }
}
