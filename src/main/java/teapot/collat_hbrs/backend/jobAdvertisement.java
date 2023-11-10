package teapot.collat_hbrs.backend;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

@Entity
@Embeddable
public class jobAdvertisement extends Account {

    // Warte auf Antwort von PO bzgl. Anforderungen zu den Stelenangeboten.
    private String title;

    private String jobtitle;

    private String requirements;

    private int[] salaryRange = new int[2];

    private boolean homeoffice;

    private String location; // ggf. in numerisch umgewandelt, um Distanzen messen zu können

    public jobAdvertisement() {

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

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public int[] getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(int[] salaryRange) {
        this.salaryRange = salaryRange;
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
