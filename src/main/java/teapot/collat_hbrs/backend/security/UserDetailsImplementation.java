package teapot.collat_hbrs.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import teapot.collat_hbrs.backend.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImplementation implements UserDetails {

    private final Account account;
    public UserDetailsImplementation(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList= new ArrayList<>();
        for (String role: account.getAuthorities().split(";")) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return authorityList;
    }

    @Override
    public String getPassword() {
        return account.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }
}
