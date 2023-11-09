package teapot.collat_hbrs.backend.security;



import java.util.Optional;
import teapot.collat_hbrs.backend.Account;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.AccountRepository;



    @Service
    public class UserDetailServiceImplementation implements UserDetailsService {

        @Autowired
        private AccountRepository accountRepository;

        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

            if(userName.equals("admin")) return User.builder().username("admin").password("admin").build();

            Optional<Account> user = accountRepository.findByUsername(userName);
            user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));

            //TODO add functionality to read user from db
            throw new NotImplementedException();
            //return user.get();
        }

    }

