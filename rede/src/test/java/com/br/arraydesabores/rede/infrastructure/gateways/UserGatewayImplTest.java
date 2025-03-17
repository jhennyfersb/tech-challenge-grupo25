package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.entity.UserRoleEntity;
import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import com.br.arraydesabores.rede.presentation.dto.user.UserListDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserGatewayImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserGatewayImpl userGateway;

    AutoCloseable mock;

    @BeforeEach
    public void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        mock.close();
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    public void shouldSaveUserSuccessfully() {
        // Arrange
        User userDomain = new User();
        userDomain.setName("Vinicius Norberto");
        userDomain.setEmail("vinicius.norberto@gmail.com");

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDomain.getName());
        userEntity.setEmail(userDomain.getEmail());

        when(modelMapper.map(userDomain, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(userDomain);

        // Act
        User createdUser = userGateway.save(userDomain);

        // Assert
        assertNotNull(createdUser);
        assertEquals("Vinicius Norberto", createdUser.getName());
        assertEquals("vinicius.norberto@gmail.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Deve criar um consumidor com sucesso")
    public void shouldCreateConsumerSuccessfully() {
        // Arrange

        User userDomain = new User();
        userDomain.setName("Vinicius Norberto");
        userDomain.setEmail("vinicius.norberto@gmail.com");
        var roleAdmin = new UserRole();
        roleAdmin.setRole(UserRoleType.CONSUMER);
        var roles = new HashSet<UserRole>();
        roles.add(roleAdmin);
        userDomain.setRoles(roles);

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDomain.getName());
        userEntity.setEmail(userDomain.getEmail());

        var roleAdminEntity = new UserRoleEntity();
        roleAdminEntity.setRole(UserRoleType.CONSUMER);
        roleAdminEntity.setUser(userEntity);
        var rolesEntities = new HashSet<UserRoleEntity>();
        rolesEntities.add(roleAdminEntity);
        userEntity.setRoles(rolesEntities);

        when(modelMapper.map(userDomain, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(userDomain);

        // Act
        User createdUser = userGateway.createConsumer(userDomain);

        // Assert
        assertNotNull(createdUser);
        assertTrue(createdUser.getRoles().stream().anyMatch(role -> role.getRole().equals(UserRoleType.CONSUMER)));
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Deve encontrar todos os usuários paginados")
    void shouldFindAllUsersPaginated() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Vinicius Norberto");
        userEntity.setEmail("vinicius.norberto@gmail.com");
        Page<UserEntity> userEntities = new PageImpl<>(List.of(userEntity));

        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setId(1L);
        userListDTO.setName("Vinicius Norberto");
        userListDTO.setEmail("vinicius.norberto@gmail.com");

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userEntities);
        when(modelMapper.map(userEntity, UserListDTO.class)).thenReturn(userListDTO);

        // Act
        Page<UserListDTO> usersPage = userGateway.findAll(Pageable.unpaged());

        // Assert
        assertNotNull(usersPage);
        assertEquals(1, usersPage.getContent().size());
        assertEquals("Vinicius Norberto", usersPage.getContent().getFirst().getName());
    }

    @Test
    @DisplayName("Deve encontrar um usuário por id com sucesso")
    void shouldFindUserByIdSuccessfully() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Vinicius Norberto");

        User user = new User();
        user.setId(1L);
        user.setName("Vinicius Norberto");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        // Act
        User foundUser = userGateway.findById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals("Vinicius Norberto", foundUser.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    public void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userGateway.findById(1L));
    }

    @Test
    void update() {
    }

    @Test
    @DisplayName("Deve verificar se um usuário existe por email")
    void shouldCheckIfUserExistsByEmail() {
        // Arrange
        when(userRepository.existsByEmail("vinicius.norberto@gmail.com")).thenReturn(true);
    }

    @Test
    @DisplayName("Deve encontrar um usuário por email")
    void shouldFindUserByEmail() {

        String EMAIL_DEFAULT = "vinicius.norberto@gmail.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail(EMAIL_DEFAULT);

        User user = new User();
        user.setId(1L);
        user.setEmail(EMAIL_DEFAULT);

        when(userRepository.findByEmail(EMAIL_DEFAULT)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        // Act
        User foundUser = userGateway.findByEmail(EMAIL_DEFAULT);

        // Assert
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado por email")
    public void shouldThrowExceptionWhenUserEmailNotFound() {
        // Arrange
        when(userRepository.findByEmail("vinicius.norberto@gmail.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userGateway.findByEmail("vinicius.norberto@gmail.com"));
    }
}