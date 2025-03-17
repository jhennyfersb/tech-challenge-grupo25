package com.br.arraydesabores.rede.infrastructure.security;


import com.br.arraydesabores.rede.application.exception.ForbiddenException;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityUtils {

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserAuthDTO) {
            return ((UserAuthDTO) principal).getLogin();
        } else {
            return principal.toString();
        }
    }

    public static UserAuthDTO getCurrentUserAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserAuthDTO) {
                return ((UserAuthDTO) principal);
            }
        }
        throw new ForbiddenException("Usuário não autenticado");
    }

}
