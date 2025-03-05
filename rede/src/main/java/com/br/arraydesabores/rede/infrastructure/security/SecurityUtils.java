package com.br.arraydesabores.rede.infrastructure.security;


import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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

}
