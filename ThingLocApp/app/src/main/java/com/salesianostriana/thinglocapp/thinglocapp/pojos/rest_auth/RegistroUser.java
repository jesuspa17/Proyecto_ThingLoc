package com.salesianostriana.thinglocapp.thinglocapp.pojos.rest_auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 17/02/2016.
 */
public class RegistroUser {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password1")
    @Expose
    private String password1;
    @SerializedName("password2")
    @Expose
    private String password2;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    /**
     * No args constructor for use in serialization
     */
    public RegistroUser() {
    }

    /**
     * @param lastName
     * @param password1
     * @param username
     * @param password2
     * @param email
     * @param firstName
     */
    public RegistroUser(String username, String password1, String password2, String email, String firstName, String lastName) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The password1
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * @param password1 The password1
     */
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    /**
     * @return The password2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * @param password2 The password2
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}