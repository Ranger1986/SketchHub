package com.example.sketchhub;
public class User {
    private String username;
    private String email;
    private int age;
    private String password;
    private boolean premium;

    // Constructeur
    public User(String username, String email, int age, String password, boolean premium) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.password = password;
        this.premium = premium;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPremium() {
        return premium;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
