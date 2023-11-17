package teapot.collat_hbrs.backend;

public class AccountBuilder {

    //shared values
    private String username;
    private String email;
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

    public AccountBuilder(String username, String email) {
        this.username = username;
        this.email = email;
    }


    public Account buildCompany(){
        return new Company(username, email, companyName, address, phoneNumber, companyIndustry, companyDescription);
    }

    public Account buildStudent(){
        return new Student(username, email, surname, forename, address, phoneNumber, studyProgram);
    }

    public AccountBuilder setAddress(String address){
        this.address = address;
        return this;
    }

    public AccountBuilder setStudentName(String forename, String surname){
        this.forename = forename;
        this.surname = surname;
        return this;
    }

    public AccountBuilder setCompanyName(String companyName){
        this.companyName = companyName;
        return this;
    }

    public AccountBuilder setPhoneNumber(){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public AccountBuilder setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
        return this;
    }

    public AccountBuilder setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
        return this;
    }

    public AccountBuilder setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
        return this;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
