package org.example.model;

public class User {
	
	private int id;
    private String username;
    private String email;
    private String password;

    public User() {
    }
    
    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setUsername(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }
    
}
