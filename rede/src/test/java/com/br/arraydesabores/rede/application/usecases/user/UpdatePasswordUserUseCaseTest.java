package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import com.br.arraydesabores.rede.presentation.dto.ChangePasswordDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import com.br.arraydesabores.rede.presentation.dto.userRole.UserRoleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePasswordUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdatePasswordUserUseCase updatePasswordUserUseCase;

    @BeforeEach
    void setUp() {
        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        userAuth.setPassword("encodedOldPassword");
        var userRole = new UserRoleDTO();
        userRole.setRole(UserRoleType.OWNER);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Deve trocar a senha com sucesso")
    public void shouldChangePasswordSuccessfully() {
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String encodedPassword = "encodedNewPassword";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(oldPassword, newPassword);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setId(1L);
        userAuthDTO.setPassword(encodedPassword);

        User userEntity = new User();
        userEntity.setId(userAuthDTO.getId());
        userEntity.setPassword(userAuthDTO.getPassword());

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = Mockito.mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUserAuth).thenReturn(userAuthDTO);

            when(passwordEncoder.matches(oldPassword, userAuthDTO.getPassword())).thenReturn(true);
            when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
            when(modelMapper.map(userAuthDTO, User.class)).thenReturn(userEntity);

            updatePasswordUserUseCase.execute(changePasswordDTO);

            verify(passwordEncoder, times(1)).matches(oldPassword, userAuthDTO.getPassword());
            verify(passwordEncoder, times(1)).encode(newPassword);
            verify(userGateway, times(1)).save(userEntity);

        }

    }

    @Test
    void shouldThrowExceptionWhenOldPasswordIsIncorrect() {
        String oldPassword = "wrongPassword";
        String newPassword = "newPassword";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(oldPassword, newPassword);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setId(1L);
        userAuthDTO.setPassword("encodedOldPassword");

        when(passwordEncoder.matches(oldPassword, userAuthDTO.getPassword())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updatePasswordUserUseCase.execute(changePasswordDTO);
        });

        assertEquals("Senha incorreta", exception.getMessage());
        verify(passwordEncoder, times(1)).matches(oldPassword, userAuthDTO.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userGateway, never()).save(any());
    }
}