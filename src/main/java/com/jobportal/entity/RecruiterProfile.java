package com.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {

    @Id
    private int recruiterProfileId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User userId;

    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;
    @Column(nullable = true, length = 64)
    private String photoProfile;

    public RecruiterProfile() {
    }

    public RecruiterProfile(int recruiterProfileId, User userId, String firstName, String lastName, String city, String state, String country, String company, String photoProfile) {
        this.recruiterProfileId = recruiterProfileId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.company = company;
        this.photoProfile = photoProfile;
    }

    public RecruiterProfile(User user) {
        this.userId = user;
    }

    public int getRecruiterProfileId() {
        return recruiterProfileId;
    }

    public void setRecruiterProfileId(int recruiterProfileId) {
        this.recruiterProfileId = recruiterProfileId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }
}
