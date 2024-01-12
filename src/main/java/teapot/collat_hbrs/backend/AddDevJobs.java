package teapot.collat_hbrs.backend;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import java.util.Random;


@Component
@Profile("dev")
public class AddDevJobs implements InitializingBean {

    private final JobAdvertisementService jobAdvertisementService;
    private final CompanyRepository companyRepository;

    public AddDevJobs(JobAdvertisementService jobAdvertisementService, CompanyRepository companyRepository) {
        this.jobAdvertisementService = jobAdvertisementService;
        this.companyRepository = companyRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Random random = new Random();

        Company test_company = new Company("microsoft", "", "Microsoft", "Cologne", "", "", "");
        companyRepository.save(test_company);

        for (int x = 0; x < 10; x++) {
            // Demo job advertisements
            JobAdvertisement ad = new JobAdvertisement();
            ad.setCompany(test_company);
            ad.setLocation("Cologne");
            ad.setTitle("Test Job");
            ad.setFullOrPartTime("Part Time");

            ad.setHourlywage(Math.round(random.nextDouble(30) * 10.0) / 10.0);

            jobAdvertisementService.addJobAdvertisement(ad);

        }
    }
}
