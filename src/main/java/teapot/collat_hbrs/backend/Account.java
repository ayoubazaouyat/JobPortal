package teapot.collat_hbrs.backend;

import jakarta.persistence.*;

@Entity
@DiscriminatorColumn(name = "ACCOUNT_TYPE")
@Inheritance(strategy = InheritanceType.JOINED) //(strategy=InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID") // Use 'ID' as Primary Key
    private long id;
    protected String username;
    private String password;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    // Make this setter protected so other classes do not interfere with JPA
    private void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {  return enabled;}

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
