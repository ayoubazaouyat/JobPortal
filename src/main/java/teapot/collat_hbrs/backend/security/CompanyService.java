package teapot.collat_hbrs.backend.security;

import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.CompanyRepository;

import java.util.List;
import java.util.Set;

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

    public List<Company> getFilteredCompanies(String name, Set<String> location, Set<String> catogory) {
        List<Company> companies = getAllCompanies();

        //remove entries that do not fit the search criteria if it is bot empty
        //TODO make filter match on similarity not equality
        if(!name.equals("")){
            for (Company company: companies) {
                if(company.getCompanyName().equals(name))
                    companies.remove(company);
            }
        }


        companies = companies.stream().filter(company -> company.getCompanyName().equals(name)).toList();

        return companies;
    }


}
