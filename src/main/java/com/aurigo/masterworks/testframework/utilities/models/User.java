package com.aurigo.masterworks.testframework.utilities.models;

import java.util.List;

public class User {

    public String username;
    public String password;
    public List<String> roles;
    public boolean isBusy;

    // Getters and setters
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

    @Override
    public String toString() {
        return "User: {" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", isBusy=" + isBusy +
                '}';
    }
}

