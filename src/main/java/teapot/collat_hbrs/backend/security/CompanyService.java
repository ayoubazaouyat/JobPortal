package teapot.collat_hbrs.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public Company findByName(List<Company> companies, String companyName) {
        for (Company company : companies) {
            if (company.getCompanyName().equals(companyName)) {
                return company;
            }
        }
        throw new IllegalArgumentException("Company with name '" + companyName + "' not found");
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


    public Company addCompany(Company company) {
        if (company.getCompanyName() == null || company.getCompanyName().trim().isEmpty()) {
            throw new IllegalArgumentException("Please assign the company name");
        }
        return companyRepository.save(company);
    }
}
