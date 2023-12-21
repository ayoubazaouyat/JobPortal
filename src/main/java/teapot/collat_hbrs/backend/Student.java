package teapot.collat_hbrs.backend;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("S")
public class Student extends Account {
    private String surname;
    private String forename;
    private String address; // consisting of streetName, houseNumber, postalCode, city
    private String phoneNumber;
    private String studyProgram;

    @ElementCollection
    private List<String> skills;
    protected Student() {
        //default constructor required for tests
    }

    public Student(String username, String email, String surname, String forename, String address, String phoneNumber,
                   String studyProgram) {
        setUsername(username);
        setEmail(email);
        this.surname = surname;
        this.forename = forename;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.studyProgram = studyProgram;
        this.skills = new ArrayList<>();
        setAuthorities("STUDENT");
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String x) {
        skills.add(x);
    }

    public void deleteSkill(String x) {
        skills.remove(x);
    }
}


