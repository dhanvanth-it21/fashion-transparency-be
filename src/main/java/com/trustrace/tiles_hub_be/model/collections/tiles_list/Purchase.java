package com.trustrace.tiles_hub_be.model.collections.tiles_list;

import com.trustrace.tiles_hub_be.model.collections.Actor.Supplier;
import com.trustrace.tiles_hub_be.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "purchases")
public class Purchase {

    @Id
    private String _id;

    private String purchaseId; // this is just a id related to the purchase (not included in this project)

    private String supplierId; //referencing the supplier

    private List<PurchaseItem> itemList; // list of items in this shipment

    private double damagePercentage; //acceptable damage allowed in the shipment

    private String recordedByUserId; //referencing the user who is recordered the list in the system

    private String approvedByUserId; // referencing the user who approved it

    @CreatedDate
    private Date createdAt;
}
