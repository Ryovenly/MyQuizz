package com.example.myquizz.model;

public class User {
    private String firstName;

    @Override

    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
