package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.CompanyRepository;
import teapot.collat_hbrs.backend.security.CompanyService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyServiceTest {
    Company c1;
    Company c2;
    List<Company> companies;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;

    @BeforeEach
    public void setup() {
        companyRepository.deleteAll();
        c1 = new Company("comp1", "comp1@business.com", "COMP1", "Musterstrasse 41a 53757 Sankt Augustin", "0224112345", "IT", "Great company to work at");
        c2 = new Company("comp2", "comp2@business.com", "COMP2", "Musterstrasse 41b 53757 Sankt Augustin", "0224112344", "IT", "Better company to work at");
        companies = Arrays.asList(c1, c2);
        companyRepository.saveAll(companies);
    }

    @AfterEach
    public void teardown() {
        companyRepository.deleteAll(companies);
        companies = null;
        c1 = null;
        c2 = null;
    }

    @Test
    public void findCompanyByNameAndNameIsCorrect() {
        assertEquals(c1, companyService.findByName("COMP1"));
        assertEquals(c2, companyService.findByName("COMP2"));
    }

    @Test
    public void findCompanyByNameButNameIsFalse() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> companyService.findByName("COMP3"));
        assertEquals("Company with name 'COMP3' not found", e.getMessage());
    }

    @Test
    public void findAllCompanies() {
        //when(companyRepository.findAll()).thenReturn(companies); // This might be better but doesn't work somehow
        //when(companyService.getAllCompanies()).thenReturn(companies);
        List<Company> all = companyService.getAllCompanies();


        assertNotNull(all);
        assertEquals(2, all.size());
        assertTrue(all.contains(c1));
        assertTrue(all.contains(c2));
    }

    @Test
    public void getCompaniesSelectedByFilter() {
        //when(companyService.getAllCompanies()).thenReturn(companies);
        List<Company> filtered = companyService.getFilteredCompanies("MP1", new HashSet<>(), new HashSet<>());
        assertEquals(1, filtered.size());
        assertEquals("COMP1", filtered.get(0).getCompanyName());
        // TODO Add tests for location and category as soon as according filtering is implemented in  backend function
    }
}
