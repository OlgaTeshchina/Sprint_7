package org.example.models;

public class Courier {
    String login;
    String password;
    String firstName;

    public Courier withPassword(String password){
        this.password = password;
        return this;
    }

    public Courier withLogin(String login){
        this.login = login;
        return this;
    }

    public Courier withFirstName(String firstName){
        this.firstName = firstName;
        return  this;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
