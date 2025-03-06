package com.br.arraydesabores.rede.infrastructure.security;

import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    final TokenService tokenService;
    final ModelMapper modelMapper;
    final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Filtrando requisição para: " + request.getRequestURI());

        try {
            var token = this.recoverToken(request);

            if (token == null) {
                logger.warn("Token não encontrado na requisição para: " + request.getRequestURI());
                filterChain.doFilter(request, response); // Passar para o próximo filtro sem autenticação
                return;
            }

            logger.info("Token encontrado, validando...");

            var login = tokenService.validateToken(token);
            if (login == null) {
                logger.warn("Token inválido ou expirado para: " + request.getRequestURI());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido ou expirado");
                return;
            }

            UserAuthDTO user = userRepository.findByEmail(login)
                    .map(entity -> modelMapper.map(entity, UserAuthDTO.class))
                    .orElseThrow(() -> new RuntimeException("User Not Found"));

            logger.info("Usuário autenticado: " + user.getEmail());

            var authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Erro no filtro de segurança: " + e.getMessage(), e);
            throw e;
        }
    }


    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (Objects.isNull(authHeader)) return null;
        return authHeader.replace("Bearer ", "");
    }
}