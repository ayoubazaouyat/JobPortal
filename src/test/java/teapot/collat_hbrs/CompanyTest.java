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
    JobAdvertisement j1, j2;

    @BeforeEach
    public void setup() {
        c1 = new Company(1L,"acme", "b@examlpe.com", "ACME", "Musterstrasse 41a 53757 Sankt Augustin",
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
    void list_is_empty_when_company_is_created() {
        assertEquals(0, c1.getJobAdvertisements().size());
    }

    @Test
    void list_is_incremented_when_jobAdvert_is_added() {
        assertEquals(0, c1.getJobAdvertisements().size());
        c1.postJobAdvertisement(j1);
        assertEquals(1, c1.getJobAdvertisements().size());
    }

    @Test
    void list_is_decremented_when_jobAdvert_is_deleted() {
        assertEquals(0, c1.getJobAdvertisements().size());
        c1.postJobAdvertisement(j1);
        assertEquals(1, c1.getJobAdvertisements().size());
        c1.deleteJobAdvertisement(j1);
        assertEquals(0, c1.getJobAdvertisements().size());
    }

    @Test
    void jobAdvert_is_correctly_added() {
        c1.postJobAdvertisement(j1);
        assertEquals(j1, c1.getJobAdvertisements().get(0));
        c1.postJobAdvertisement(j2);
        assertEquals(j2, c1.getJobAdvertisements().get(1));
    }

    @Test
    void jobAdvert_is_correctly_deleted() {
        c1.postJobAdvertisement(j1);
        assertEquals(j1, c1.getJobAdvertisements().get(0));
        c1.postJobAdvertisement(j2);
        assertEquals(j2, c1.getJobAdvertisements().get(1));
        c1.deleteJobAdvertisement(j1);
        assertEquals(j2, c1.getJobAdvertisements().get(0));
    }
}
