package teapot.collat_hbrs.backend.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import teapot.collat_hbrs.backend.Student;

@Component
@Profile("dev")
public class AddDevUser implements InitializingBean {


    private final UserService userService;

    @Autowired
    public AddDevUser(UserService userService) {
        this.userService = userService;
    }

    public void removeDevUser() {
        userService.deleteAccount("admin");
    }

    @Override
    public void afterPropertiesSet() {
        Student testuser = new Student("admin", "admin@test.test", "admin", "admin", "", "", "");
        userService.registerAccount(testuser, "admin");
    }

}
