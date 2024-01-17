package teapot.collat_hbrs.backend;


import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
//@Embeddable
@DiscriminatorValue("J")
public class JobAdvertisement implements Serializable {

    @ManyToOne
    @JoinColumn(name = "companyName")
    private Company company;
    @Id
    @GeneratedValue
    private Long jobAdvertisementId;
    private String title;
    private String textDescription;
    private String  fullOrPartTime;
    private String remoteOrInHouse;
    private String requirements;
    private double hourlywage;
    private boolean homeoffice;
    private java.time.LocalDate offerAge;
    private String expectations;
    private Date starttime;
    private String candidateCount;
    private String employeeBenefits;
    private String location; // ggf. in numerisch umgewandelt, um Distanzen messen zu können

    private String name;


    public String getName() { return name; }
    public void setName(String name) { this.name = name;}
    public String getFullOrPartTime() {
        return fullOrPartTime;
    }

    public void setFullOrPartTime(String fullOrPartTime) {
        this.fullOrPartTime = fullOrPartTime;
    }

    public Long getJobAdvertisementId() {
        return jobAdvertisementId;
    }

    public void setJobAdvertisementId(Long jobAdvertisementId) {
        this.jobAdvertisementId = jobAdvertisementId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String description) {
        this.textDescription = description;
    }

    public double getHourlywage() {
        return hourlywage;
    }

    public void setHourlywage(double hourlywage) {
        this.hourlywage = hourlywage;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public boolean isHomeoffice() {
        return homeoffice;
    }

    public void setHomeoffice(boolean homeoffice) {
        this.homeoffice = homeoffice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemoteOrInHouse() {
        return remoteOrInHouse;
    }

    public void setRemoteOrInHouse(String remoteOrInHouse) {
        this.remoteOrInHouse = remoteOrInHouse;
    }

    public String getExpectations() {
        return expectations;
    }

    public void setExpectations(String expectations) {
        this.expectations = expectations;
    }

    public String getCandidateCount() {
        return candidateCount;
    }

    public void setCandidateCount(String candidateCount) {
        this.candidateCount = candidateCount;
    }

    public String getEmployeeBenefits() {
        return employeeBenefits;
    }

    public void setEmployeeBenefits(String employeeBenefits) {
        this.employeeBenefits = employeeBenefits;
    }

    public java.time.LocalDate getOfferAge() {
        return offerAge;
    }

    public void setOfferAge(java.time.LocalDate offerAge) {
        this.offerAge = offerAge;
    }

}
