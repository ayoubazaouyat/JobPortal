package teapot.collat_hbrs.backend;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("C")
public class Company extends Account{

    private String companyName;
    private String address; // consisting of streetName, houseNumber, postalCode, city
    private String industry; // Branche
    private String companyDescription;
    private String phoneNumber;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobAdvertisement> jobAdvertisements;

    protected Company() {}

    public Company(String username, String email, String companyName, String address, String phoneNumber, String industry, String companyDescription) {
        setUsername(username);
        setEmail(email);
        this.companyName = companyName;
        this.address = address;
        this.industry = industry;
        this.companyDescription = companyDescription;
        this.phoneNumber = phoneNumber;
        this.jobAdvertisements = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String landlineNumber) {
        this.phoneNumber = landlineNumber;
    }

    public void setJobAdvertisements(List<JobAdvertisement> jobAdvertisements) {
        this.jobAdvertisements = jobAdvertisements;
    }

    public List<JobAdvertisement> getJobAdvertisements() {
        return jobAdvertisements;
    }

    public void postJobAdvertisement(JobAdvertisement x) {
        jobAdvertisements.add(x);
    }

    public void deleteJobAdvertisement(JobAdvertisement x) {
        jobAdvertisements.remove(x);
    }


    // um wichtige Stellenanzeigen hervorzuheben
    public void pushJobAdvertisement(JobAdvertisement x) {
        deleteJobAdvertisement(x);
        jobAdvertisements.add(0, x);
    }





}

