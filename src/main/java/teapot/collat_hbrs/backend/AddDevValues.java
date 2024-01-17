package teapot.collat_hbrs.backend;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import teapot.collat_hbrs.backend.security.ChatMessageService;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;
import teapot.collat_hbrs.backend.security.UserService;

import java.util.Random;


@Component
@Profile("dev")
public class AddDevValues implements InitializingBean {

    private final JobAdvertisementService jobAdvertisementService;
    private final UserService userService;
    private final ChatMessageService chatMessageService;
    private final Random random;

    public AddDevValues(JobAdvertisementService jobAdvertisementService, UserService userService, ChatMessageService chatMessageService) {
        this.jobAdvertisementService = jobAdvertisementService;
        this.userService = userService;
        this.chatMessageService = chatMessageService;
        random = new Random();
    }

    @Override
    public void afterPropertiesSet() {


        Student testuser = new Student("admin", "admin@test.test", "admin", "admin", "", "", "");
        userService.registerAccount(testuser, "admin");

        Company testCompany = new Company("admin2", "", "Microsoft", "Cologne", "", "", "");
        userService.registerAccount(testCompany, "admin2");


        chatMessageService.addChatMessage(new ChatMessage("admin2", "admin", "I have a question about the job posting...", "2023-01-02 12:30"));
        chatMessageService.addChatMessage(new ChatMessage("admin", "admin2", "Hello, I'm interested in the job...", "2023-01-01 10:00"));

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
