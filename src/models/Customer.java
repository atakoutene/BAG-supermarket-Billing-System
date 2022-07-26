/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author atako
 */
public class Customer implements Comparable<Customer>{
    private String id;
    private String fname;
    private String lname;
    private String email;
    private Date dob;
    private Integer phoneNumber;
    private Integer qtyPurchased;
    private String pwd;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Customer(String id, String fname, String lname, String email, int phoneNumber, int qtyPurchased,String pwd) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qtyPurchased = qtyPurchased;
        this.pwd = pwd;
    }

    public Customer(String id, String fname, String lname, String email, Date dob, Integer phoneNumber, Integer qtyPurchased, String pwd) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.qtyPurchased = qtyPurchased;
        this.pwd = pwd;
    }
    
    

    public Customer(String refNumber, String pwd) {
        this.id = refNumber;
        this.pwd = pwd;
    }

    public Customer(String refNumber, String fname, String lname, String email, int phoneNumber, int qtyPurchased) {
        this.id = refNumber;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qtyPurchased = qtyPurchased;
    }



    public Customer() {
    }

    public String getPwd() {
        return pwd;
    }

    public String getRefNumber() {
        return id;
    }

    public void setRefNumber(String refNumber) {
        this.id = refNumber;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getQtyPurchased() {
        return qtyPurchased;
    }

    public void setQtyPurchased(int qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    @Override
    public int compareTo(Customer t) {
        return this.qtyPurchased.compareTo(t.qtyPurchased);
    }
    
    @Override
    public String toString(){
        if(this.fname == null){
            return this.lname + " " + this.id + " " + this.dob + " " + this.phoneNumber;
        }
        return this.fname + " " + this.lname + " " + this.id + " " + this.dob + " " + this.phoneNumber;
        
    }
    
}
