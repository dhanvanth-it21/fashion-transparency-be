package com.trustrace.tiles_hub_be.model.collections.activityLogs.orderTracking;

import com.trustrace.tiles_hub_be.model.collections.tiles_list.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
