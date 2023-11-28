package teapot.collat_hbrs;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import teapot.collat_hbrs.backend.security.SecurityService;
import java.util.Collection;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SecurityServiceTest {

    @Mock
    private AuthenticationContext authenticationContext;

    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityService = new SecurityService(authenticationContext);
    }

    @Test
    void getAuthenticatedUser() {
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        when(authenticationContext.getAuthenticatedUser(UserDetails.class)).thenReturn(Optional.of(userDetails));
        assertEquals(userDetails, securityService.getAuthenticatedUser());
    }

    @Test
    void isAuthenticated() {
        when(authenticationContext.isAuthenticated()).thenReturn(true);
        assertTrue(securityService.isAuthenticated());
    }

    @Test
    void logout() {
        securityService.logout();
        assertTrue(true);
    }
}