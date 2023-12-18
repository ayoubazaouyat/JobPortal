package teapot.collat_hbrs.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.Account;
import teapot.collat_hbrs.backend.AccountRepository;

import java.util.Optional;

@Service
public class UserService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAccount(Account account, String password) {
        if(account.getUsername().isEmpty())
            throw new IllegalArgumentException("empty username not allowed");
        if(password.isEmpty())
            throw new IllegalArgumentException("empty password not allowed");
        if(accountRepository.findByUsername(account.getUsername()).isPresent())
            throw new IllegalArgumentException("duplicate usernames are not allowed");

        account.setPasswordHash(passwordEncoder.encode(password));
        account.setEnabled(true);
        accountRepository.save(account);
    }

    public void deleteAccount(String username) {
        if(username.isEmpty()) {
            throw new IllegalArgumentException("Empty username not allowed");
        }
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()) {
            accountRepository.delete(account.get());
            //account.get().setEnabled(false); // Disable instead of delete
        } else {
            throw new UsernameNotFoundException("Username '" + username + "' not found.");
        }
    }

    public void changePassword(String username, String newPassword){
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()){
            account.get().setPasswordHash(passwordEncoder.encode(newPassword));
            accountRepository.save(account.get());
        }
        else {
            throw new UsernameNotFoundException("username '" + username + "' not found.");
        }
    }

}
