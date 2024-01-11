package teapot.collat_hbrs.backend.security;

import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.CompanyRepository;

import java.util.ArrayList;
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
        List<Company> filteredCompanies = new ArrayList<>();

        //remove entries that do not fit the search criteria if it is bot empty
        //TODO make filter match on similarity not equality
        if (!name.isEmpty()) {
            for (Company company : companies) {
                if (company.getCompanyName().contains(name))
                    //companies.remove(company);
                    filteredCompanies.add(company);
            }
        }

        if (!location.isEmpty()) {
            for (Company company : companies) {
                //TODO add location filter
            }
        }

        if (!catogory.isEmpty()) {
            for (Company company : companies) {
                //TODO add category filter
            }
        }

        return filteredCompanies;
    }


}
