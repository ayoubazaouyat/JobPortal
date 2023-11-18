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

    public Company createCompany(Company company) {
        // Hier könntest du Validierungen oder andere Geschäftslogik hinzufügen
        return companyRepository.save(company);
    }

    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Unternehmen nicht gefunden"));
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


    public Company addCompany(Company company) {
        if (company.getCompanyName() == null || company.getCompanyName().trim().isEmpty()) {
            throw new IllegalArgumentException("Bitte vergeben Sie den Namen Ihres Unernehmens.");
        }
        return companyRepository.save(company);
    }
}
