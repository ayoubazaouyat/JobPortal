package error_418_dvj.CollatHBRS.Backend;

import jakarta.persistence.*;

@Entity
public class Unternehmen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "unternehmensId") // Nutze unternehmensId als Primaerschluessel

    private Long unternehmensId;
    private String name;
    private String email;

    protected Unternehmen() {}

    public Unternehmen(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return unternehmensId;
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
}
