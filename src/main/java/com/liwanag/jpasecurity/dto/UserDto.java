package com.liwanag.jpasecurity.dto;

import java.util.List;

public class UserDto implements Comparable<UserDto> {

    private String username;

    private String password;
    private Boolean enabled;

    private String firstname;
    private String lastname;

    private List<AuthorityDto> authorities;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

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

    public List<AuthorityDto> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityDto> authorities) {
        this.authorities = authorities;
    }

    @Override
    public int compareTo(UserDto u) {
        if (getLastname() == null || u.getLastname() == null) {
            return 0;
        }
        return getLastname().compareTo(u.getLastname());
    }
}
