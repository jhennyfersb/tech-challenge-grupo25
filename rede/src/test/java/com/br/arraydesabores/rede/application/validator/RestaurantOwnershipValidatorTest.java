package com.br.arraydesabores.rede.application.validator;

import com.br.arraydesabores.rede.application.exception.ForbiddenException;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestaurantOwnershipValidatorTest {

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é o proprietário")
    public void shouldNotThrowExceptionWhenUserIsOwner() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        User owner = new User();
        owner.setId(1L);

        restaurant.setOwner(owner);

        UserAuthDTO currentUser = new UserAuthDTO();
        currentUser.setId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(currentUser);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> RestaurantOwnershipValidator.IsOwned(restaurant));
        assertEquals("Usuário não autenticado", exception.getMessage());
    }

}
