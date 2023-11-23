package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import teapot.collat_hbrs.backend.*;
import teapot.collat_hbrs.backend.security.UserService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    Student s1, s2, s3, s4;

    private PasswordEncoder passwordEncoder;
    private UserService userService, userServiceMock;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        s1 = new Student("", "", "Test", "Test", "", "", "");
        s2 = new Student("UserServiceTestUser", "", "Test", "Test", "", "", "");
        s3 = new Student("Duplicate", "", "Test", "Test", "", "", "");
        s4 = new Student("Duplicate", "", "Test", "Test", "", "", "");
        userServiceMock = mock(UserService.class);
        userService = new UserService();
        passwordEncoder = mock(PasswordEncoder.class);
        accountRepository.save(s3);
    }

    @AfterEach
    public void teardown() {
        s1 = null;
        s2 = null;
        s3 = null;
        s4 = null;
        userServiceMock = null;
        userService = null;
        accountRepository.deleteAll();
    }

    @Test
    void username_is_empty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerAccount(s1, "Test"));
        assertEquals("empty username not allowed", e.getMessage());
    }

    @Test
    void password_is_empty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.registerAccount(s2, ""));
        assertEquals("empty password not allowed", e.getMessage());
    }

    @Test
    void duplicate_usernames_not_allowed() {
        // Exception not thrown because check is probably not working as expected
        Exception e = assertThrows(IllegalArgumentException.class, () ->  {
            userServiceMock.registerAccount(s4, "Test");
        });
        assertEquals("duplicate usernames are not allowed", e.getMessage());
        // Here are some troubleshooting tests
        userServiceMock.registerAccount(s4, "Test");
        //assertTrue(accountRepository.findByUsername(s3.getUsername()).isPresent()); // Check if s3 is in repo -> this should succeed
        //assertTrue(accountRepository.findByUsername(s4.getUsername()).isPresent()); // Check if s4 is in repo -> this should succeed
        //assertEquals(s3.getUsername(), s4.getUsername()); // If this test succeeds, it means that usernames are equal, it should actually fail
        //assertNotEquals(s3.getUsername(), s4.getUsername()); // If this test fails, it means that the usernames are equal, it should actually succeed
    }

    @Test
    void change_password_test() {
        userServiceMock.changePassword(s3.getUsername(), "NewPassword");
        assertEquals(passwordEncoder.encode("NewPassword"), s3.getPasswordHash());

        // Exception not thrown because check is probably not working as expected
        Exception e = assertThrows(UsernameNotFoundException.class, () ->  {
            userServiceMock.changePassword("nonExistentName", "NewPassword");
        });
        assertEquals("username 'nonExistentName' not found.", e.getMessage());
        // Here is a test for verification
        // If this test succeeds, it means that an account with a non-existent username is present, it should actually fail
        //assertTrue(accountRepository.findByUsername("nonExistentName").isPresent());
    }
}
