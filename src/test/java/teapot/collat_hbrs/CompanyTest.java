package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import teapot.collat_hbrs.backend.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CompanyTest {

    Company c1;
    JobAdvertisement j1;
    JobAdvertisement j2;

    @BeforeEach
    public void setup() {
        c1 = new Company("acme", "b@examlpe.com", "ACME", "Musterstrasse 41a 53757 Sankt Augustin",
                "0224112345", "IT", "Great company to work at");
    }

    @AfterEach
    public void teardown() {
        c1 = null;
    }

    /*
    We are not testing getters and setters here, only list functionality so far
     */

    @Test
    void listIsEmptyWhenCompanyIsCreated() {
        assertEquals(0, c1.getJobAdvertisements().size());
    }

    @Test
    void listIsIncrementedWhenJobAdvertIsAdded() {
        assertEquals(0, c1.getJobAdvertisements().size());
        c1.postJobAdvertisement(j1);
        assertEquals(1, c1.getJobAdvertisements().size());
    }

    @Test
    void listIsDecrementedWhenJobAdvertIsDeleted() {
        assertEquals(0, c1.getJobAdvertisements().size());
        c1.postJobAdvertisement(j1);
        assertEquals(1, c1.getJobAdvertisements().size());
        c1.deleteJobAdvertisement(j1);
        assertEquals(0, c1.getJobAdvertisements().size());
    }

    @Test
    void jobAdvertIsCorrectlyAdded() {
        c1.postJobAdvertisement(j1);
        assertEquals(j1, c1.getJobAdvertisements().get(0));
        c1.postJobAdvertisement(j2);
        assertEquals(j2, c1.getJobAdvertisements().get(1));
    }

    @Test
    void jobAdvertIsCorrectlyDeleted() {
        c1.postJobAdvertisement(j1);
        assertEquals(j1, c1.getJobAdvertisements().get(0));
        c1.postJobAdvertisement(j2);
        assertEquals(j2, c1.getJobAdvertisements().get(1));
        c1.deleteJobAdvertisement(j1);
        assertEquals(j2, c1.getJobAdvertisements().get(0));
    }
}
