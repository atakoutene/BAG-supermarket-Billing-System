/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author leopo
 */
public class Profile {
    String fName;
    String lName;
    String dob;
    String email;
    int phoneNumber;
    
    public Profile(){}
    
    public Profile(String fName, String lName, int phoneNumber, String dob, String email ){
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.email = email;
        this.phoneNumber = phoneNumber;        
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
}
