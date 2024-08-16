/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author ayseserra
 */
public class User {
    int id;
    String username;
    String password;
    String pwagain;
    int user_type;
    
    
    public User() {
    }

    public User(int id, String username, String password, String pwagain, int user_type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.pwagain = pwagain;
        this.user_type = user_type;
    }

    // overloading
    public void displayUserInfo() {
        System.out.println("User Information:");
        System.out.println("Username: " + username);
        System.out.println("User Type: " + user_type);
    }

   
}

    
 

