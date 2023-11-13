package teapot.collat_hbrs.backend;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("C")
public class Company extends Account{
    private String name;
    private String streetName;
    private String houseNumber; // Use String because houseNumber could be 41a or similiar
    private String postalCode;
    private String city;
    private String industry; // Branche
    private String companyDescription;
    private String landlineNumber;
    private String email;


    @ElementCollection
    private List<JobAdvertisement> jobAdvertisements = new ArrayList<>();

    protected Company() {}

    public Company(String name, String username, String streetName, String houseNumber,
                   String postalCode, String city, String industry, String companyDescription,
                   String landlineNumber, String email) {
        this.name = name;
        this.username = username;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.industry = industry;
        this.companyDescription = companyDescription;
        this.landlineNumber = landlineNumber;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

