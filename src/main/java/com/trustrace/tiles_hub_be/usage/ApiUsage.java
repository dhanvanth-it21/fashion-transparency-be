package com.trustrace.tiles_hub_be.usage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "api_usage")
public class ApiUsage {
    @Id
    private String _id;

    private String apiEndPoint;
    private String apiMethod;
    private String email;
    private Double price;

    @CreatedDate
    private Date createdAt;
}
