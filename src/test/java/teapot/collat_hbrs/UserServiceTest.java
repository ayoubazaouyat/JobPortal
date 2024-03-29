package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import teapot.collat_hbrs.backend.*;
import teapot.collat_hbrs.backend.security.UserService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    Student s1;
    Student s2;
    Student s3;
    Student s4;
    Student s5;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        s1 = new Student("", "", "Test", "Test", "", "", "");
        s2 = new Student("EmptyPass", "", "Test", "Test", "", "", "");
        s3 = new Student("Duplicate", "", "Test", "Test", "", "", "");
        s4 = new Student("Change", "", "Test", "Test", "", "", "");
        s5 = new Student("Deleted", "", "Test", "Test", "", "", "");
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void teardown() {
        s1 = null;
        s2 = null;
        s3 = null;
        s4 = null;
        s5 = null;
    }

    @Test
    void usernameIsEmpty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerAccount(s1, "Test"));
        assertEquals("empty username not allowed", e.getMessage());
    }

    @Test
    void passwordIsEmpty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerAccount(s2, ""));
        assertEquals("empty password not allowed", e.getMessage());
    }

    @Test
    void duplicateUsernamesNotAllowed() {
        when(accountRepository.findByUsername(s3.getUsername())).thenReturn(Optional.of(s3));
        assertThrows(IllegalArgumentException.class, () -> userService.registerAccount(s3, "testPassword"));
    }

    @Test
    void changePasswordTest() {
        // Test password change
        when(accountRepository.findByUsername(s4.getUsername())).thenReturn(Optional.of(s4));
        userService.changePassword(s4.getUsername(), "NewPassword");
        assertEquals(passwordEncoder.encode("NewPassword"), s4.getPasswordHash());

        // Test thrown exception
        when(accountRepository.findByUsername("nonExistentName")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.changePassword("nonExistentName", "newpassword"));
    }

    @Test
    void deleteUserWhenUsernameIsEmpty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.deleteAccount(""));
        assertEquals("Empty username not allowed", e.getMessage());
    }

    @Test
    void deleteNonExistentUser() {
        when(accountRepository.findByUsername("nonExistentName")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.deleteAccount("nonExistentName"));
    }

    @Test
    void deleteUserCorrectly() {
        when(accountRepository.findByUsername(s5.getUsername())).thenReturn(Optional.of(s5));
        userService.deleteAccount(s5.getUsername());
        verify(accountRepository).delete(s5);
    }
}