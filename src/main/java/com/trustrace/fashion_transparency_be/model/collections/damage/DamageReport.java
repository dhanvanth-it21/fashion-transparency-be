package com.trustrace.fashion_transparency_be.model.collections.damage;


import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Document(collection = "damageReports")
public class DamageReport {

    @Id
    private String _id;

    private String damageReportId;

    private String tileId; //referencing the particular tile


    private DamageLocation damageLocation; // FROM_MANUFACTURER, AT_WAREHOUSE, TO_RETAIL_SHOP

    private int qty; //number of boxes

    private String remark; // remark about the damage

    private DamageStatus status; // UNDER_REVIEW, APPROVED, REJECTED

    private String orderId; // nuul when damage not reported by retailer

    private String purchaseId; // null when damage not reported by retailer

    private boolean creditNoteIssued;



    private String reportedBy; // referencing the user reported the damage

    private String approvedBy; // referencing the user approved after the inspection

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
