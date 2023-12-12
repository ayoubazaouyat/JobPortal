package teapot.collat_hbrs;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import teapot.collat_hbrs.backend.AccountRepository;
import teapot.collat_hbrs.backend.security.UserDetailServiceImplementation;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public class UserDetailServiceImplementationTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private UserDetailServiceImplementation userDetailService;

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsername() {
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("username"));
    }
}
