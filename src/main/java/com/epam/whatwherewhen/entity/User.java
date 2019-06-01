package com.epam.whatwherewhen.entity;


import java.sql.Blob;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class User extends Entity {
    private long userId;
    private String login;
    private String password;
    private long rating;
    private boolean isAdmin;
    private boolean isActive;
    private Blob photo;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, boolean isAdmin, boolean isActive) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public User(long userId, String login, String password, long rating, boolean isAdmin, boolean isActive) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.rating = rating;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public User(long userId, String login, long rating, boolean isActive, Blob photo) {
        this.userId = userId;
        this.login = login;
        this.rating = rating;
        this.isActive = isActive;
        this.photo = photo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (rating != user.rating) return false;
        if (isAdmin != user.isAdmin) return false;
        if (isActive != user.isActive) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return photo != null ? photo.equals(user.photo) : user.photo == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        result = 31 * result + (isAdmin ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                ", isAdmin=" + isAdmin +
                ", isActive=" + isActive +
                ", photo=" + photo +
                '}';
    }
}
