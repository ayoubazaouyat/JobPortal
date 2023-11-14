package teapot.collat_hbrs.backend;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("S")
public class Student extends Account {
    private String surname;
    private String forename;
    private String email;
    private String address; // consisting of streetName, houseNumber, postalCode, city
    private String phone;
    private String studyProgram;

    @ElementCollection
    private List<String> skills;
    protected Student() {
    }

    public Student(String surname, String forename, String email, String address, String phone,
                   String studyProgram, String username) {
        this.surname = surname;
        this.forename = forename;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.studyProgram = studyProgram;
        this.username = username;
        this.skills = skills;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}


