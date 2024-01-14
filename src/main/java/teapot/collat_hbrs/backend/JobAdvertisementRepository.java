package teapot.collat_hbrs.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {
        List<JobAdvertisement> findByCompanyCompanyName(String companyName);
}

