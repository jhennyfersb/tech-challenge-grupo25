package com.br.arraydesabores.rede.infra.security;

import com.br.arraydesabores.rede.model.User;
import com.br.arraydesabores.rede.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    final TokenService tokenService;

    final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if(Objects.nonNull(login)) {
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));
            var authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))  // Passando o papel completo (ex: ROLE_USER)
                    .collect(Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(Objects.isNull(authHeader)) return null;
        return authHeader.replace("Bearer ", "");
    }
}