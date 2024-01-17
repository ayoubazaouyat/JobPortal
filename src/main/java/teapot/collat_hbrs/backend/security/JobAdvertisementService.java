package teapot.collat_hbrs.backend.security;


import org.springframework.stereotype.Service;
import teapot.collat_hbrs.backend.JobAdvertisement;
import teapot.collat_hbrs.backend.JobAdvertisementRepository;

import java.util.List;

@Service
public class JobAdvertisementService {
    private final JobAdvertisementRepository jobAdvertisementRepository;

    // Method to get the list of jobs
    public JobAdvertisementService(JobAdvertisementRepository jobAdvertisementRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
    }

    public List<JobAdvertisement> getAllJobAdvertisements() {
        return jobAdvertisementRepository.findAll();
    }
    public JobAdvertisement addJobAdvertisement(JobAdvertisement jobAdvertisement) {
        if (jobAdvertisement.getTitle() == null || jobAdvertisement.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Please assign the job title");
        }
        if(jobAdvertisement.getCompany() == null) throw new IllegalArgumentException("Company must not be null");

        // Idee: Job-Listing beim Hinzufügen in Data base auch auf jeweiliges Datenfeld in Company?
        //jobAdvertisement.getCompany().pushJobAdvertisement(jobAdvertisement);

        return jobAdvertisementRepository.save(jobAdvertisement);
    }

    public void deleteJobAdvertisement(JobAdvertisement jobAdvertisement) {
        //if(jobAdvertisementRepository.exists(jobAdvertisement) {, funktioniert nicht
        jobAdvertisementRepository.delete(jobAdvertisement);
    }

    public List<JobAdvertisement> getJobAdvertisementsForCompany(String companyName) {
        return jobAdvertisementRepository.findByCompanyCompanyName(companyName);
    }

    public JobAdvertisement getJobAdvertisementById(long id){
        return jobAdvertisementRepository.findByjobAdvertisementId(id);
    }
}