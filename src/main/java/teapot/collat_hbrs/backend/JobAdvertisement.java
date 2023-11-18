package teapot.collat_hbrs.backend;


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
//@Embeddable
@DiscriminatorValue("J")
public class JobAdvertisement extends Account {

    @ManyToOne
    @JoinColumn(name = "companyName")
    private Company company;

    private Long jobAdvertisementId;
    private String title;

    private String description;

    private String requirements;

    private double hourlywage;

    private boolean homeoffice;

    private Date starttime;

    private String location; // ggf. in numerisch umgewandelt, um Distanzen messen zu können



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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
