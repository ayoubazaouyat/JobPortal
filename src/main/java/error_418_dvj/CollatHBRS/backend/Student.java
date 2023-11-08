package error_418_dvj.CollatHBRS.backend;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("S")
public class Student extends Account {
    private String surname;
    private String forename;
    private String email;

    protected Student() {}

    public Student(String surname, String forename, String username, String email) {
        this.surname = surname;
        this.forename = forename;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}