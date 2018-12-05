package com.cornell.kim;

public class Users {


    String fname, gender;
    int age;

    public Users() {
    }

    public Users(String fname, int age, String gender) {
        this.fname = fname;
        this.age = age;
        this.gender = gender;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
