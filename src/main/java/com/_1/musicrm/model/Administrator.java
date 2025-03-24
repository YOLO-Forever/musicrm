package com._1.musicrm.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Temporal(TemporalType.DATE)
    private Integer adminId;
    private String adminName;
    private String adminPassword;
    private String adminEmail;
    private Date adminDob;
    private Integer adminStatus; //0=active; 1=inactive; 2=deleted

    public Administrator(){
    }  
    
    public Administrator(Integer adminId, String adminName, String adminPassword, String adminEmail, Date adminDob, Integer adminStatus) {
        this.adminId = adminId;
        this.adminName = adminName; 
        this.adminPassword = adminPassword;
        this.adminEmail = adminEmail;
        this.adminDob = adminDob;
        this.adminStatus = adminStatus;
    }

    public Integer getAdminId() {
        return adminId;
    }
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminEmail() {
        return adminEmail;
    }
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Date getAdminDob() {
        return adminDob;
    }
    public void setAdminDob(Date adminDob) {
        this.adminDob = adminDob;
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }
    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }
}