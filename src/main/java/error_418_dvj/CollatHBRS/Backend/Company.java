package error_418_dvj.CollatHBRS.Backend;

import jakarta.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "companyId") // Use 'companyId' as Primary Key
    /* companyId is annotated by '@Id' which tells our persistence provider to
    access our field 'companyId' directly.
    Therefore, we don't need to annotate our getters and setters for the id.
    */
    private Long companyId;
    private String name;
    private String username;
    private String email;

    protected Company() {}

    public Company(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return companyId;
    }

    // Make this setter protected so other classes do not interfere with JPA
    protected void setId(Long companyId) {
        this.companyId = companyId;
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
