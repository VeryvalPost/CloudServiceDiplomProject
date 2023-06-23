package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.entity.User;
import netology.cloudserverdiplom.error.AuthorizeError;
import netology.cloudserverdiplom.logger.LoggerClass;
import netology.cloudserverdiplom.model.AuthorizeData;
import netology.cloudserverdiplom.model.Token;
import netology.cloudserverdiplom.repository.UserRepo;
import netology.cloudserverdiplom.security.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class AuthorizeServiceTest {

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AuthorizeService authorizeService;

    @BeforeEach
    public void setup() {
        this.userRepo = mock(UserRepo.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_SuccessfulAuthorization() {
        String login = "testUser";
        String password = "testPassword";
        String authToken = "testAuthToken";

        AuthorizeData authorizeData = new AuthorizeData(login, password);
        User user = new User();
        user.setPassword(password);

        when(userRepo.findByLogin(login)).thenReturn(user);
        when(jwtUtil.generateToken(login, password)).thenReturn(authToken);

        Token result = authorizeService.login(authorizeData);

        verify(userRepo, times(1)).saveAndFlush(user);
        Assertions.assertEquals(authToken, user.getAuthToken());
        Assertions.assertEquals(authToken, result.getToken());
    }

    @Test
    public void testLogin_WrongLoginOrPassword() {
        String login = "testUser";
        String password = "testPassword";

        AuthorizeData authorizeData = new AuthorizeData(login, password);
        User user = new User();
        user.setPassword("wrongPassword");

        when(userRepo.findByLogin(login)).thenReturn(user);

        Assertions.assertThrows(AuthorizeError.class, () -> authorizeService.login(authorizeData));

        verify(userRepo, never()).saveAndFlush(user);
    }

    @Test
    public void testLogout() {
        String login = "testUser";
        User user = new User();
        user.setAuthToken("testAuthToken");

        when(userRepo.findByLogin(login)).thenReturn(user);

        authorizeService.logout(login);

        verify(userRepo, times(1)).saveAndFlush(user);
        Assertions.assertEquals("null", user.getAuthToken());
    }
}