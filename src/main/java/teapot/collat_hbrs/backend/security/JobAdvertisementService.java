package teapot.collat_hbrs.backend.security;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.JobAdvertisementRepository;

@Service
public class JobAdvertisementService {
    private final JobAdvertisementRepository jobAdvertisementRepository;

    private EntityManager entityManager;


    @Autowired
    public JobAdvertisementService(JobAdvertisementRepository jobAdvertisementRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
    }

    public JobAdvertisement addJobAdvertisement(JobAdvertisement jobAdvertisement) {
        if (jobAdvertisement.getTitle() == null || jobAdvertisement.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Bitte vergeben Sie einen Stellentitel.");
        }

        return jobAdvertisementRepository.save(jobAdvertisement);
    }
}