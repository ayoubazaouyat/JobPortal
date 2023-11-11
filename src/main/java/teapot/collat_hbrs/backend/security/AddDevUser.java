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


    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired

    public AddDevUser(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() {
        Student testuser = new Student("admin", "admin", "admin", "");
        testuser.setPassword(passwordEncoder.encode("admin"));
        testuser.setEnabled(true);
        studentRepository.save(testuser);
    }

}
