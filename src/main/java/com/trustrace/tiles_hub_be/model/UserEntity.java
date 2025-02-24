package com.trustrace.tiles_hub_be.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String name;
    private String emailId;
    private String password;
    private Boolean isActive;
    private String phone;
    private String address;
    private Date createdDate;
    private Date updatedDate;

    private Set<Role> roles = new HashSet<>();

    public UserEntity(String name, String emailId, String password) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.isActive = false;
        this.createdDate = new Date();
    }

    public UserEntity(String name, String emailId, String password, String phone, String address) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.isActive = false;
        this.createdDate = new Date();
    }


    //----------------------getters and setters-----------------------

//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmailId() {
//        return emailId;
//    }
//
//    public String getPassword() {
//        return password;
//    }

    public Boolean getActive() {
        return isActive;
    }

//    public String getPhone() {
//        return phone;
//    }

//    public String getAddress() {
//        return address;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public Date getUpdatedDate() {
//        return updatedDate;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }

    public void setName(String name) {
        this.name = name;
        this.updatedDate = new Date();
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
        this.updatedDate = new Date();
    }

    public void setPassword(String password) {
        this.password = password;
        this.updatedDate = new Date();
    }

    public void setActive(Boolean active) {
        isActive = active;
        this.updatedDate = new Date();
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedDate = new Date();
    }

    public void setAddress(String address) {
        this.address = address;
        this.updatedDate = new Date();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
        this.updatedDate = new Date();
    }
}
