package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import teapot.collat_hbrs.backend.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AccountCreatorTest {

    AccountCreator studentCreator;
    AccountCreator companyCreator;
    Student studentAccount;
    Company companyAccount;
    String address = "Grantham-Allee 20 53757 Sankt Augustin";
    String password = "P4ssw0rd";
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        studentCreator = new AccountCreator();
        companyCreator = new AccountCreator();

        studentCreator.setUsername("StudentBuilder");
        studentCreator.setEmail("student@test.com");
        studentCreator.setSurname("Sample");
        studentCreator.setForename("Test");
        studentCreator.setAddress(address);
        studentCreator.setPhoneNumber("0000 1234567");
        studentCreator.setStudyProgram("CS");
        studentCreator.setPassword(password);

        companyCreator.setUsername("CompanyBuilder");
        companyCreator.setEmail("company@test.com");
        companyCreator.setCompanyName("Sample GmbH");
        companyCreator.setAddress(address);
        companyCreator.setPhoneNumber("02241 12345");
        companyCreator.setCompanyIndustry("IT");
        companyCreator.setCompanyDescription("Best");
        companyCreator.setPassword(password);

        studentAccount = (Student) studentCreator.buildStudent();
        companyAccount = (Company) companyCreator.buildCompany();
    }

    @AfterEach
    public void teardown() {
        studentAccount = null;
        companyAccount = null;
    }

    @Test
    void testBuilderForStudent() {
        assertEquals("StudentBuilder", studentAccount.getUsername());
        assertEquals("student@test.com", studentAccount.getEmail());
        assertEquals("Sample", studentAccount.getSurname());
        assertEquals("Test", studentAccount.getForename());
        assertEquals(address, studentAccount.getAddress());
        assertEquals("0000 1234567", studentAccount.getPhoneNumber());
        assertEquals("CS", studentAccount.getStudyProgram());
        assertEquals(passwordEncoder.encode(password), studentAccount.getPasswordHash());
    }

    @Test
    void testBuilderForCompany() {
        assertEquals("CompanyBuilder", companyAccount.getUsername());
        assertEquals("company@test.com", companyAccount.getEmail());
        assertEquals("Sample GmbH", companyAccount.getCompanyName());
        assertEquals(address, companyAccount.getAddress());
        assertEquals("02241 12345", companyAccount.getPhoneNumber());
        assertEquals("IT", companyAccount.getIndustry());
        assertEquals("Best", companyAccount.getCompanyDescription());
        assertEquals(passwordEncoder.encode(password), companyAccount.getPasswordHash());
    }
}
