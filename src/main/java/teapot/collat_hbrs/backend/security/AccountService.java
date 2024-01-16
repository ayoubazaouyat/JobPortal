package teapot.collat_hbrs.backend.security;

import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.Account;
import teapot.collat_hbrs.backend.AccountRepository;

@Service
public class AccountService {
    private final SecurityService securityService;
    private final AccountRepository accountRepository;
    public AccountService(SecurityService securityService, AccountRepository accountRepository) {
        this.securityService = securityService;
        this.accountRepository = accountRepository;
    }
    public Account getAccount(){
        String username = securityService.getAuthenticatedUser().getUsername();
        return accountRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }
}
