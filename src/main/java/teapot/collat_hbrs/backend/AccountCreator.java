package teapot.collat_hbrs.backend;

public class AccountCreator {

    //shared values
    private String username;
    private String email;
    private String password;
    private String street;
    private String houseNr;
    private String plz;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;
    private String address;
    private String phoneNumber;

    //student only
    private String surname;
    private String forename;
    private String studyProgram;

    //company only
    private String companyName;
    private String companyIndustry;
    private String companyDescription;


    public Account buildCompany() {
        if(address.isEmpty())
            address = street +" "+ houseNr +" "+ plz +" "+ city ;
        return new Company(username, email, companyName, address, phoneNumber, companyIndustry, companyDescription);
    }

    public Account buildStudent() {
        if(address.isEmpty())
            address = street +" "+ houseNr +" "+ plz +" "+ city ;
        return new Student(username, email, surname, forename, address, phoneNumber, studyProgram);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getForename() {
        return forename;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }
}
