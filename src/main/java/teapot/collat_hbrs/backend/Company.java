package teapot.collat_hbrs.backend;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("C")
public class Company extends Account{
    private String name;
    private String address; // consisting of streetName, houseNumber, postalCode, city
    private String industry; // Branche
    private String companyDescription;
    private String landlineNumber;
    private String email;


    @ElementCollection
    private List<JobAdvertisement> jobAdvertisements;

    protected Company() {}

    public Company(String name, String username, String address, String industry, String companyDescription,
                   String landlineNumber, String email) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.industry = industry;
        this.companyDescription = companyDescription;
        this.landlineNumber = landlineNumber;
        this.email = email;
        this.jobAdvertisements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJobAdvertisements(List<JobAdvertisement> jobAdvertisements) {
        this.jobAdvertisements = jobAdvertisements;
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

