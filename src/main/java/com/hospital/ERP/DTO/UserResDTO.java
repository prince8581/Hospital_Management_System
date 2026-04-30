package com.hospital.ERP.DTO;

import com.hospital.ERP.Entity.Users;

public class UserResDTO {

    private int id;
    private String name;
    private String email;
    private long phoneNo;

    private Users.Role role;
    private Users.Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
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
