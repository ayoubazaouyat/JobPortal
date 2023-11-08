package error_418_dvj.CollatHBRS;

import error_418_dvj.CollatHBRS.Backend.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CompanyRepository companyRepository;
	@Test
	void repositoryTest() {

		Student s1 = new Student("Musterman","Max","mmuster","a@example.com");
		Company c1 = new Company("ACME", "acme","b@examlpe.com");

		assertEquals(accountRepository.count(),0);
		assertEquals(studentRepository.count(),0);
		assertEquals(companyRepository.count(),0);

		studentRepository.save(s1);

		assertEquals(accountRepository.count(),1);
		assertEquals(studentRepository.count(),1);
		assertEquals(companyRepository.count(),0);

		companyRepository.save(c1);

		assertEquals(accountRepository.count(),2);
		assertEquals(studentRepository.count(),1);
		assertEquals(companyRepository.count(),1);

		accountRepository.delete(s1);
		accountRepository.delete(c1);

		assertEquals(accountRepository.count(),0);
		assertEquals(studentRepository.count(),0);
		assertEquals(companyRepository.count(),0);

	}

}
