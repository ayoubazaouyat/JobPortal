package error_418_dvj.CollatHBRS.Backend;

import jakarta.persistence.*;

@Entity
public class Unternehmen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "unternehmensId") // Use 'unternehmensId' as Primary Key
    /* unternehmensId is annotated by '@Id' which tells our persistence provider to
    access our field 'unternehmensId' directly.
    Therefore, we don't need to annotate our getters and setters for the id.
    */
    private Long unternehmensId;
    private String name;
    private String username;
    private String email;

    protected Unternehmen() {}

    public Unternehmen(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return unternehmensId;
    }

    // Make this setter protected so other methods do not interfere with JPA
    protected void setId(Long unternehmensId) {
        this.unternehmensId = unternehmensId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername(String username) {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
