package com.example.chathome.modal;

public class Users {
    String firstname;
    String lastname;
    String id;

    String company;

    public Users() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Users{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", id='" + id + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
