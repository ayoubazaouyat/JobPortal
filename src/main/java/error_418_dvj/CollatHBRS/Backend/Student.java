package error_418_dvj.CollatHBRS.Backend;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sId") // Use 'sId' as Primary Key
    /* sId is annotated by '@Id' which tells our persistence provider to access our field 'sId' directly.
    Therefore, we don't need to annotate our getters and setters for the id.
    */
    private Long sId; // Matriculation Number
    private String surname;
    private String forename;
    private String username;
    private String email;

    protected Student() {}

    public Student(String surname, String forename, String username, String email) {
        this.surname = surname;
        this.forename = forename;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return sId;
    }

    // Make this setter protected so other classes do not interfere with JPA
    protected void setId(Long sId) {
        this.sId = sId;
    }

    public String getSurname() { return surname; }

    public void setName(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getUsername() {
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