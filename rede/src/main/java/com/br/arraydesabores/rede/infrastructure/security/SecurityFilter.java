package com.br.arraydesabores.rede.infrastructure.security;

import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    final TokenService tokenService;
    final ModelMapper modelMapper;
    final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (Objects.nonNull(login)) {
            UserAuthDTO user = userRepository.findByEmail(login)
                    .map(entity -> modelMapper.map(entity, UserAuthDTO.class))
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            var authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (Objects.isNull(authHeader)) return null;
        return authHeader.replace("Bearer ", "");
    }
}