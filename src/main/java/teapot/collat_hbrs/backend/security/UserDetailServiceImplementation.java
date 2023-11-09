package teapot.collat_hbrs.backend.security;


import java.util.Optional;

import teapot.collat_hbrs.backend.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.AccountRepository;


@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    private final AccountRepository  accountRepository;

    @Autowired
    protected UserDetailServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //if(userName.equals("admin")) return User.builder().username("admin").password("admin").build();

        Optional<Account> user = accountRepository.findByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));

        return new UserDetailsImplementation(user.get());
    }



}

