package teapot.collat_hbrs.backend;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("C")
public class Company extends Account{

    // Company eigenen Unique Identifier geben?
    private String companyName;
    private String address; // consisting of streetName, houseNumber, postalCode, city
    private String industry; // Branche
    private String companyDescription;
    private String phoneNumber;
    private double rating;
    private double numberofrating;




    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobAdvertisement> jobAdvertisements;

    protected Company() {
        //default constructor required for jpa and tests
    }

    public Company(String username, String email, String companyName, String address, String phoneNumber, String industry, String companyDescription) {
        setUsername(username);
        setEmail(email);
        this.companyName = companyName;
        this.address = address;
        this.industry = industry;
        this.companyDescription = companyDescription;
        this.phoneNumber = phoneNumber;
        this.jobAdvertisements = new ArrayList<>();
        this.rating=0;
        this.numberofrating = 0;
        setAuthorities("COMPANY");

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
        deleteJobAdvertisement(x); // auskommentieren fürs Pushen auf Liste?
        jobAdvertisements.add(0, x);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null &&
                obj.getClass().equals(Company.class) &&
                ((Company)obj).getId().equals(this.getId()));
    }


    public void addRating(double ratingValue) {
        numberofrating =numberofrating+1;
        rating=(rating+ratingValue)/numberofrating;

    }

    public double getAverageRating() {
        return rating;

    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getNumberofrating() {
        return numberofrating;
    }

    public void setNumberofrating(double numberofrating) {
        this.numberofrating = numberofrating;
    }
}

