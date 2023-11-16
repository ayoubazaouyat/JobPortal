package teapot.collat_hbrs.backend.security;


import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public UserDetails getAuthenticatedUser() {
        Optional<UserDetails> userDetails = authenticationContext.getAuthenticatedUser(UserDetails.class);
        userDetails.orElseThrow(() -> new RuntimeException("userDetails not found"));
        return userDetails.get();
    }

    public boolean isAuthenticated(){
        return authenticationContext.isAuthenticated();
    }

    public void logout() {
        authenticationContext.logout();
    }
}