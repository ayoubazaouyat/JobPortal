package teapot.collat_hbrs.backend;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

import java.sql.Date;
import java.sql.Time;

@Entity
@Embeddable
public class JobAdvertisement extends Account {

    // Warte auf Antwort von PO bzgl. Anforderungen zu den Stelenangeboten.
    private String title;

    private String description;

    private String requirements;

    private double hourlywage;

    private boolean homeoffice;

    private Date starttime;

    private String location; // ggf. in numerisch umgewandelt, um Distanzen messen zu können

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
