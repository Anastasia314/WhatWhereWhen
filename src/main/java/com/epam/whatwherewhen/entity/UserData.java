package com.epam.whatwherewhen.entity;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class UserData extends Entity {
    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private long birthdate;
    private String city;

    public UserData() {
    }

    public UserData(long userId, String email, String firstName, String lastName, long birthdate) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public UserData(long userId, String email, String firstName, String lastName, long birthdate, String city) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.city = city;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;

        UserData userData = (UserData) o;

        if (userId != userData.userId) return false;
        if (birthdate != userData.birthdate) return false;
        if (email != null ? !email.equals(userData.email) : userData.email != null) return false;
        if (firstName != null ? !firstName.equals(userData.firstName) : userData.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userData.lastName) : userData.lastName != null) return false;
        return city != null ? city.equals(userData.city) : userData.city == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (int) (birthdate ^ (birthdate >>> 32));
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", city='" + city + '\'' +
                '}';
    }
}
