/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author atako
 */
public class Admin {
    private String id;
    private String fname;
    private String lname;
    private String pwd;

    public Admin() {
    }

    public Admin(String id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    public Admin(String id, String fname, String lname, String pwd) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.pwd = pwd;
    }

    public Admin(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPwd() {
        return pwd;
    }
    
    
    
    
}
