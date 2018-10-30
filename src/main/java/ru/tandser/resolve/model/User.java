package ru.tandser.resolve.model;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;

public class User {

    public static final String NAME      = "name";
    public static final String SURNAME   = "surname";
    public static final String BIRTHDATE = "birthdate";

    private String    name;
    private String    surname;
    private LocalDate birthdate;

    private User() {}

    public User(String name, String surname, LocalDate birthdate) {
        this.name      = name;
        this.surname   = surname;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name",      name)
                .add("surname",   surname)
                .add("birthdate", birthdate)
                .toString();
    }

    //<editor-fold desc="Setters and Getters">

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    //</editor-fold>
}