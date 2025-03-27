package com.trustrace.fashion_transparency_be.model.collections.tiles_list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "purchases")
public class Purchase {

    @Id
    private String _id;


    private String purchaseId;

    private String supplierId; //referencing the supplier

    private List<PurchaseItem> itemList; // list of items in this shipment

    private double damagePercentage; //acceptable damage allowed in the shipment

    private String recordedBy; //referencing the user who is recordered the list in the system

    private String approvedBy; // referencing the user who approved it

    private PurchaseStatus status; // status of the purchase

    @CreatedDate
    private Date createdAt;
}
