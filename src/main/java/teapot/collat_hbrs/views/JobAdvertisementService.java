package teapot.collat_hbrs.views;

import teapot.collat_hbrs.backend.JobAdvertisement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobAdvertisementService {
    private List<JobAdvertisement> jobAdvertisements;

    public JobAdvertisementService() {
        this.jobAdvertisements = new ArrayList<>();
    }

    public void addJobAdvertisement(JobAdvertisement jobAdvertisement) {
        jobAdvertisements.add(jobAdvertisement);
    }

    public List<JobAdvertisement> getJobAdvertisementsForCompany(String companyName) {
        return jobAdvertisements.stream()
                .filter(job -> job.getCompany().getCompanyName().equals(companyName))
                .collect(Collectors.toList());
    }

}
