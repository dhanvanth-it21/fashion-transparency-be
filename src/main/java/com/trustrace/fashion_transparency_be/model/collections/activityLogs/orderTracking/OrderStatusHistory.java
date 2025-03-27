package com.trustrace.fashion_transparency_be.model.collections.activityLogs.orderTracking;

import com.trustrace.fashion_transparency_be.model.collections.tiles_list.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusHistory {

    private OrderStatus status;

    private String changedBy;

    private Date changedAt;
}
