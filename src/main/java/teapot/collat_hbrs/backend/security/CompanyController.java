
package teapot.collat_hbrs.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.security.CompanyService;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final JobAdvertisementService jobAdvertisementService;

    @Autowired
    public CompanyController(CompanyService companyService, JobAdvertisementService jobAdvertisementService) {
        this.companyService = companyService;
        this.jobAdvertisementService = jobAdvertisementService;
    }


    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }


    @GetMapping("/{companyName}/jobAdvertisements")
    public List<JobAdvertisement> getJobAdvertisements(@PathVariable String companyName) {
        Company company = companyService.findByName(getCompanies(), companyName);
        return company.getJobAdvertisements();
    }

    @GetMapping("/companies")
    public List<Company> getCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return companies;
    }

    @PostMapping("/{companyName}/jobAdvertisements")
    public JobAdvertisement addJobAdvertisement(@PathVariable String companyName, @RequestBody JobAdvertisement jobAdvertisement) {
        Company company = companyService.findByName(getCompanies(), companyName);
        jobAdvertisement.setCompany(company);
        return jobAdvertisementService.addJobAdvertisement(jobAdvertisement);
    }



}