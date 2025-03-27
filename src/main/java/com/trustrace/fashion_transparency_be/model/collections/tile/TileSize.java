package com.trustrace.fashion_transparency_be.model.collections.tile;

public enum TileSize {

    SIZE_1X1("1x1"),
    SIZE_1_5X1("1.5x1"),
    SIZE_2X1("2x1"),
    SIZE_2X2("2x2"),
    SIZE_3X3("3x3"),
    SIZE_4X2("4x2"),
    SIZE_4X4("4x4"),
    SIZE_6X4("6x4"),
    SIZE_8X4("8x4");

    private final String size;


    TileSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public static TileSize fromString(String size) throws IllegalAccessException {
        for(TileSize s : TileSize.values()) {
            if(s.size.equals(size)) {
                return s;
            }
        }
        throw new IllegalAccessException("Invalid Size entered : " + size);
    }



}
