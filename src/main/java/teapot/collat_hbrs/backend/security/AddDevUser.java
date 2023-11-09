package teapot.collat_hbrs.backend.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import teapot.collat_hbrs.backend.Student;
import teapot.collat_hbrs.backend.StudentRepository;

@Component
@Profile("dev")
public class AddDevUser implements InitializingBean {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void afterPropertiesSet() {
        Student testuser = new Student("admin", "admin", "admin", "");
        testuser.setPassword(passwordEncoder.encode("admin"));
        testuser.setEnabled(true);
        studentRepository.save(testuser);
    }

}
