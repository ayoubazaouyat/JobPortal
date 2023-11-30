package teapot.collat_hbrs.backend;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "ACCOUNT_TYPE")
@Inheritance(strategy = InheritanceType.JOINED) //(strategy=InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID") // Use 'ID' as Primary Key
    private  long id;
    private String username;
    private String passwordHash;
    private String email;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    // Make this setter protected so other classes do not interfere with JPA
    /*private void setId(Long id) {
        this.id = id;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {  return enabled;}

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
