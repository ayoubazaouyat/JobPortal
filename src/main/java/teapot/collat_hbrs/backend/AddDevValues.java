package teapot.collat_hbrs.backend;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;
import teapot.collat_hbrs.backend.security.UserService;

import java.util.Random;


@Component
@Profile("dev")
public class AddDevValues implements InitializingBean {

    private final JobAdvertisementService jobAdvertisementService;
    private final UserService userService;

    public AddDevValues(JobAdvertisementService jobAdvertisementService, UserService userService) {
        this.jobAdvertisementService = jobAdvertisementService;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        Random random = new Random();

        Student testuser = new Student("admin", "admin@test.test", "admin", "admin", "", "", "");
        userService.registerAccount(testuser, "admin");

        Company testCompany = new Company("admin2", "", "Microsoft", "Cologne", "", "", "");
        userService.registerAccount(testCompany, "admin2");


        for (int x = 0; x < 10; x++) {
            // Demo job advertisements
            JobAdvertisement ad = new JobAdvertisement();
            ad.setCompany(testCompany);
            ad.setLocation("Cologne");
            ad.setTitle("Test Job");
            ad.setFullOrPartTime("Part Time");

            ad.setHourlywage(Math.round(random.nextDouble(30) * 10.0) / 10.0);

            jobAdvertisementService.addJobAdvertisement(ad);

        }
    }
}
