package com.backend.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    public User()
    {
        this.id = null;
        this.username = null;
        this.password = null;
    }
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public boolean correctInfo(String username, String pass)
    {
        return this.username.equals(username) && this.password.equals(pass);
    }

    public User rmPassword()
    {
        this.password = "";
        return this;
    }
}
