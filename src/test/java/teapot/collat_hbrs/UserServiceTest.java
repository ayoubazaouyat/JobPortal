package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teapot.collat_hbrs.backend.*;
import teapot.collat_hbrs.backend.security.UserService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    Student s1, s2, s3, s4;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        s1 = new Student("", "", "Test",
                "Test", "", "", "");
        s2 = new Student("UserServiceTestUser", "", "Test",
                "Test", "", "", "");
        s3 = new Student("Duplicate", "", "Test",
                "Test", "", "", "");
        s4 = new Student("Duplicate", "", "Test",
                "Test", "", "", "");
        userService = new UserService();
    }

    @AfterEach
    public void teardown() {
        s1 = null;
        s2 = null;
        s3 = null;
        s4 = null;
        userService = null;
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

/*    @Test
    void duplicate_usernames_not_allowed() {
        accountRepository.save(s3);
        IllegalArgumentException exception;
        exception = null;
        try {
            userService.registerAccount(s4, "Test");
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertEquals("duplicate usernames are not allowed", exception.getMessage());
    } */

/*    @Test
    void change_password_test() {
        userService.registerAccount(s3, "Pre");
        accountRepository.save(s3);
        userService.changePassword(s3.getUsername(), "Post");
        assertEquals("Post", s3.getPasswordHash());
    }*/
}
