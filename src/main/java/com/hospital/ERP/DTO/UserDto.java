package com.hospital.ERP.DTO;

import com.hospital.ERP.Entity.Users;

import java.time.LocalDateTime;

public class UserDto {

    private String name;
    private String email;
    private String password;
    private long phoneNo;
    private LocalDateTime createdAT;
    private LocalDateTime updatedAT;

    private Users.Role role;
    private Users.Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public LocalDateTime getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(LocalDateTime updatedAT) {
        this.updatedAT = updatedAT;
    }

    public Users.Role getRole() {
        return role;
    }

    public void setRole(Users.Role role) {
        this.role = role;
    }

    public Users.Status getStatus() {
        return status;
    }

    public void setStatus(Users.Status status) {
        this.status = status;
    }

}
