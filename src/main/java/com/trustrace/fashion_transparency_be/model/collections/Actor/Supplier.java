package com.trustrace.fashion_transparency_be.model.collections.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "suppliers")
public class Supplier {

    @Id
    private String _id;

    private String brandName;
    private String contactPersonName;
    private String email;
    private String phone;
    private String address;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;


    public Supplier(String brandName, String contactPersonName, String email, String phone, String address) {
        this.brandName = brandName;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
