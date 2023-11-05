package error_418_dvj.CollatHBRS.Backend;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "matrNr") // Use 'matrNr' as Primary Key
    /* matrNr is annotated by '@Id' which tells our persistence provider to access our field 'matrNr' directly.
    Therefore, we don't need to annotate our getters and setters for the id.
    */
    private Long matrNr;
    private String name;
    private String vorname;
    private String username;
    private String email;

    protected Student() {}

    public Student(String name, String vorname, String username, String email) {
        this.name = name;
        this.vorname = vorname;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return matrNr;
    }

    // Make this setter protected so other methods do not interfere with JPA
    protected void setId(Long matrNr) {
        this.matrNr = matrNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.vorname = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}