package teapot.collat_hbrs;

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
        studentCreator.setAddress("Grantham-Allee 20 53757 Sankt Augustin");
        studentCreator.setPhoneNumber("0000 1234567");
        studentCreator.setStudyProgram("CS");
        studentCreator.setPassword("P4ssw0rd");

        companyCreator.setUsername("CompanyBuilder");
        companyCreator.setEmail("company@test.com");
        companyCreator.setCompanyName("Sample GmbH");
        companyCreator.setAddress("Grantham-Allee 20 53757 Sankt Augustin");
        companyCreator.setPhoneNumber("02241 12345");
        companyCreator.setCompanyIndustry("IT");
        companyCreator.setCompanyDescription("Best");
        companyCreator.setPassword("P4ssw0rd");

        studentAccount = (Student) studentCreator.buildStudent();
        companyAccount = (Company) companyCreator.buildCompany();
    }

    @BeforeEach
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
        assertEquals("Grantham-Allee 20 53757 Sankt Augustin", studentAccount.getAddress());
        assertEquals("0000 1234567", studentAccount.getPhoneNumber());
        assertEquals("CS", studentAccount.getStudyProgram());
        assertEquals(passwordEncoder.encode("P4ssw0rd"), studentAccount.getPasswordHash());
    }

    @Test
    void testBuilderForCompany() {
        assertEquals("CompanyBuilder", companyAccount.getUsername());
        assertEquals("company@test.com", companyAccount.getEmail());
        assertEquals("Sample GmbH", companyAccount.getCompanyName());
        assertEquals("Grantham-Allee 20 53757 Sankt Augustin", companyAccount.getAddress());
        assertEquals("02241 12345", companyAccount.getPhoneNumber());
        assertEquals("IT", companyAccount.getIndustry());
        assertEquals("Best", companyAccount.getCompanyDescription());
        assertEquals(passwordEncoder.encode("P4ssw0rd"), companyAccount.getPasswordHash());
    }
}
