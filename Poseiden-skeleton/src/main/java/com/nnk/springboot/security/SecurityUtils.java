package com.nnk.springboot.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Utilitaire permettant de récupérer des informations sur l'utilisateur lors de sa connection.
 * <p>
 */
@Slf4j
public final class SecurityUtils {

    private SecurityUtils() {
    }


    public static String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userName = userDetails.getUsername();

        }
        return userName;
    }

    public static boolean isAdminConnected() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String authority = authentication.getAuthorities().toString();
        if (authority.contains("ADMIN")) {
            return true;

        } else {
            return false;
        }

    }

}
