package teapot.collat_hbrs.backend;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("C")
public class Company extends Account{
    private String name;
    private String email;


    @ElementCollection
    private List<JobAdvertisement> JobAdvertisements = new ArrayList<>();

    protected Company() {}

    public Company(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJobAdvertisements(List<JobAdvertisement> JobAdvertisements) {
        this.JobAdvertisements = JobAdvertisements;
    }

    public void postJobAdvertisement(JobAdvertisement x) {
        JobAdvertisements.add(x);
    }

    public void deleteJobAdvertisement(JobAdvertisement x) {
        JobAdvertisements.remove(x);
    }


    // um wichtige Stellenanzeigen hervorzuheben
    public void pushJobAdvertisement(JobAdvertisement x) {
        deleteJobAdvertisement(x);
        JobAdvertisements.add(0, x);
    }





}

