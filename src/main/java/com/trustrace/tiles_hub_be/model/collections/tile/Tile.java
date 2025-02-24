package com.trustrace.tiles_hub_be.model.collections.tile;

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
@Document(collection = "tiles")
public class Tile {

    @Id
    private String _id;


    private String skuCode; //auto generated
    private TileSize tileSize; //enum
    private String brandId;
    private String modelName;
    private String color;
    private int qty; // number of boxes
    private int piecesPerBox;
    private TileCategory category; // enum
    private TileSubCategory subCategory; // enum
    private Finishing finishing;

    @CreatedDate
    private Date createdAt = new Date();

    @LastModifiedDate
    private Date updatedAt = new Date();


    public Tile(Finishing finishing, TileSubCategory subCategory, TileCategory category, int piecesPerBox, int qty, String color, String modelName, String brandId, TileSize tileSize, String skuCode) {
        this.finishing = finishing;
        this.subCategory = subCategory;
        this.category = category;
        this.piecesPerBox = piecesPerBox;
        this.qty = qty;
        this.color = color;
        this.modelName = modelName;
        this.brandId = brandId;
        this.tileSize = tileSize;
        this.skuCode = skuCode;
    }


}
