package com.trustrace.tiles_hub_be.model.collections.Actor;

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
@Document(collection = "retailerShops")
public class RetailerShop {
    @Id
    private String _id;

    private String shopName;
    private String contactPersonName;
    private String email;
    private String phone;
    private String address;
    private double creditNote;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;


    public RetailerShop(String shopName, String contactPersonName, String email, String phone, String address, double creditNote) {
        this.shopName = shopName;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.creditNote = creditNote;
    }
}
