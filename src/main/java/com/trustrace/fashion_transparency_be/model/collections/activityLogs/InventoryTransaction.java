package com.trustrace.fashion_transparency_be.model.collections.activityLogs;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "inventoryTransactions")  // Maps this class to the "inventoryTransactions" collection in MongoDB
public class InventoryTransaction {

    @Id
    private String _id;

    private TransactionType transactionType; //STOCK_IN, STOCK_OUT, DAMAGE_REPORT

    private String trackId;  // Reference to purchaseId, orderId, or damageReportId

    @CreatedDate
    private Date createdAt;
}
