package com.liwanag.jpasecurity.model;

import javax.persistence.*;

@Entity
@Table(name="Profile")
public class Profile {
    private String firstname;
    private String lastname;

    @Id
    private String username;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
