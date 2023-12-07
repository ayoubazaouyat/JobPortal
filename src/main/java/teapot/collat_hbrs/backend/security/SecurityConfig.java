package teapot.collat_hbrs.backend.security;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import teapot.collat_hbrs.backend.AccountRepository;
import teapot.collat_hbrs.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity


@Configuration
public class SecurityConfig extends VaadinWebSecurity {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
        */

        super.configure(http);
        setLoginView(http, LoginView.class);

        http
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/login"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 32, 100000, 512);
        encoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
        return encoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(AccountRepository accountRepository) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(new UserDetailServiceImplementation(accountRepository));
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AccountRepository accountRepository) {
        DaoAuthenticationProvider authenticationProvider = authenticationProvider(accountRepository);

        return new ProviderManager(authenticationProvider);
    }
}