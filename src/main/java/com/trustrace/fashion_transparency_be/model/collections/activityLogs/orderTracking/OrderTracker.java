package com.trustrace.fashion_transparency_be.model.collections.activityLogs.orderTracking;


import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orderTracker")
public class OrderTracker {
    @Id
    private String _id;

    private String orderId;

    private OrderStatus currentStatus;

    private String createdBy;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private List<OrderStatusHistory> statusHistory = new ArrayList<>();
}
