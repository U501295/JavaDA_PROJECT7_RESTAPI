package com.nnk.springboot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static boolean isAdminConnected() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String authority = authentication.getAuthorities().toString();
        if (authority.contains("ADMIN")) {
            log.debug("L'utilisateur connecté ne dispose pas des droits d'admin");
            return true;

        } else {
            log.debug("L'utilisateur connecté dispose des droits d'admin");
            return false;
        }

    }

}