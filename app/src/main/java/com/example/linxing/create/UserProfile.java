package com.example.linxing.create;

/**
 * Created by jiana on 2018/2/27.
 */

public class UserProfile {
    private String username_profile;
    private String age_profile;
    private String weight_profile;
    public UserProfile() {

    }
    public UserProfile(String username, String age, String weight) {
        this.username_profile = username;
        this.age_profile = age;
        this.weight_profile = weight;
    }
    public UserProfile(String username) {
        this.username_profile = username;
    }
    public void setUsername_profile(String username) {
        this.username_profile = username;
    }
    public void setAge_profile(String age) {
        this.age_profile = age;
    }
    public void setWeight_profile(String height) {
        this.weight_profile = height;
    }
    public String getAge_profile() {
        return age_profile;
    }
    public String getWeight_profile() {
        return weight_profile;
    }
    public String getUsername_profile() {
        return username_profile;
    }
}
