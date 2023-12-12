package teapot.collat_hbrs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.JobAdvertisementRepository;
import teapot.collat_hbrs.backend.security.JobAdvertisementService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class JobAdvertisementServiceTest {


    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;

    @InjectMocks
    private JobAdvertisementService jobAdvertisementService;


    @Test
    void addJobAdvertisementValidJob() {
        // Mock data
        JobAdvertisement job = new JobAdvertisement();
        job.setTitle("Test");
        // Stubbing the save method of the repository
        when(jobAdvertisementRepository.save(job)).thenReturn(job);

        // Call the service method
        JobAdvertisement result = jobAdvertisementService.addJobAdvertisement(job);

        // Verify the result
        assertEquals(job, result);
    }

    @Test
    void addJobAdvertisementInvalidJobTitle() {
        // Mock data
        JobAdvertisement job = new JobAdvertisement();

        // Call the service method and expect an exception
        assertThrows(IllegalArgumentException.class, () -> jobAdvertisementService.addJobAdvertisement(job));

        // Verify that the repository save method was not called
        verify(jobAdvertisementRepository, never()).save(job);
    }

    @Test
    void deleteJobAdvertisement() {
        // Mock data
        JobAdvertisement job = new JobAdvertisement();

        // Call the service method
        jobAdvertisementService.deleteJobAdvertisement(job);

        // Verify that the repository delete method was called with the correct argument
        verify(jobAdvertisementRepository).delete(job);
    }
}
