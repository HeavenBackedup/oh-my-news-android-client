package com.example.wangyan.oh_my_news_android_client.Bean;

/**
 * Created by wangyan on 2017/4/19.
 */
public class User {
    Integer  id;
    String username;
    String password;
    String photoid;
    String[] address;
    String email;

    public User() {
    }

    public User(Integer id, String username, String password, String photoid, String[] address, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.photoid = photoid;
        this.address = address;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoid() {
        return photoid;
    }

    public String[] getAddress() { return address;}

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }


    public void setAddress(String[] address) { this.address = address;}

    public void setEmail(String email) {
        this.email = email;
    }
}
