package com.example.linxing.create;

/**
 * Created by jiana on 2018/2/27.
 */

public class UserProfile {
    private String username_profile;
    private int age_profile;
    private int height_profile;
    public UserProfile(String username) {
        this.username_profile = username;
    }
    public void setAge_profile(int age) {
        this.age_profile = age;
    }
    public void setHeight_profile(int height) {
        this.height_profile = height;
    }

    public int getAge_profile() {
        return age_profile;
    }

    public int getHeight_profile() {
        return height_profile;
    }

    public String getUsername_profile() {
        return username_profile;
    }
}
