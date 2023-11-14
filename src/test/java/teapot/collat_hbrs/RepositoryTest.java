package teapot.collat_hbrs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teapot.collat_hbrs.backend.*;

import java.util.ArrayList;
import java.util.List;

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

		Student s1 = new Student("Musterman","Max","a@example.com",
								"Musterstrasse 41a 53757 Sankt Augustin", "01513142271",
								"Computer Science", "mmuster");
		Company c1 = new Company("ACME", "acme", "Musterstrasse 41a 53757 Sankt Augustin",
								"IT", "Great company to work at", "0224112345",
								"b@examlpe.com");

		accountRepository.deleteAll();

		assertEquals(accountRepository.count(),0);
		assertEquals(studentRepository.count(),0);
		assertEquals(companyRepository.count(),0);

		accountRepository.save(s1);

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
