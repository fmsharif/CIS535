package com.budgetmanagementsystem;

public class User {
    protected int userid;
    protected String username;
    protected String password_encrypted;

    public User(int userid, String username, String password_encrypted)
    {
        this.userid = userid;
        this.username = username;
        this.password_encrypted = password_encrypted;
    }


}
