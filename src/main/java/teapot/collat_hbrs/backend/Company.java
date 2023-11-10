package teapot.collat_hbrs.backend;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@DiscriminatorValue("C")
public class Company extends Account{
    private String name;
    private String email;

    protected Company() {}

    public Company(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}

