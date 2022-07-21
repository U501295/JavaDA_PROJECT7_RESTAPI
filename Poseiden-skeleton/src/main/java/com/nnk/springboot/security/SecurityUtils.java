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


    public static String getUserMail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userName = userDetails.getUsername();

        }
        log.debug("Negative Balance exception trigerred");
        return userName;
    }

    public static boolean isUserConnected() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String authority = authentication.getAuthorities().toString();
        if (authority.equals("[ROLE_ANONYMOUS]")) {
            log.debug("No connected user recognized");
            return false;

        } else {
            log.debug("Recognized connected user");
            return true;
        }

    }
}
