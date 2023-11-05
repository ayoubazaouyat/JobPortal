package error_418_dvj.CollatHBRS.Backend;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "matrNr") // Nutze matrNr als Primaerschluessel
    private Long matrNr;
    private String name;
    private String vorname;
    private String email;

    protected Student() {}

    public Student(String name, String vorname, String email) {
        this.name = name;
        this.vorname = vorname;
        this.email = email;
    }

    public Long getId() {
        return matrNr;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}