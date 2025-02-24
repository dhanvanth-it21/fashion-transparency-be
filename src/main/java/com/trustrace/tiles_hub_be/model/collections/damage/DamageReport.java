package com.trustrace.tiles_hub_be.model.collections.damage;


import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "damageReports")
public class DamageReport {

    @Id
    private String _id;

    private String tileId; //referencing the particular tile

    private DamageLocation damageLocation; // FROM_MANUFACTURER, AT_WAREHOUSE, TO_RETAIL_SHOP

    private int qty; //number of boxes

    private String remark; // remark about the damage

    private DamageStatus status; // UNDER_REVIEW, APPROVED, REJECTED

    private String reportedByUserId; // referencing the user reported the damage

    private String approvedByUserId; // referencing the user approved after the inspection

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
