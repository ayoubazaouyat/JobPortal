package teapot.collat_hbrs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import teapot.collat_hbrs.backend.security.AddDevUser;
import teapot.collat_hbrs.backend.security.UserService;

import static org.mockito.Mockito.*;

@SpringBootTest
@SpringJUnitConfig
@ActiveProfiles("dev")
public class AddDevUserTest {

    @Test
    public void testAfterPropertiesSet() {
        // Arrange
        UserService userServiceMock = mock(UserService.class);
        AddDevUser addDevUser = new AddDevUser(userServiceMock);
        addDevUser.removeDevUser();

        // Act
        addDevUser.afterPropertiesSet();


        // Verify that registerAccount method was called with the expected arguments
        verify(userServiceMock, times(1)).registerAccount(any(), eq("admin"));
    }
}
