package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.builder.retailshop.RetailShopNameDto;
import com.trustrace.tiles_hub_be.builder.tile.TileDetailDto;
import com.trustrace.tiles_hub_be.builder.tile.TileQtyDto;
import com.trustrace.tiles_hub_be.builder.tile.TileTableDto;
import com.trustrace.tiles_hub_be.model.collections.damage.DamageLocation;
import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.TileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tiles")
public class TileController {

    @Autowired
    private TileService tileService;

    @PostMapping
    public ResponseEntity<ApiResponse<Tile>> createTile(@RequestBody Tile tile) {
        Tile createdTile = tileService.createTile(tile);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseUtil.success("Tile Registered Successfully", createdTile, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tile>> getTile(@PathVariable String id) {
        Tile tile = tileService.getTileById(id);
        return tile != null ?
                ResponseEntity.ok(ResponseUtil.success("Tile Fetched", tile, null)) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ResponseUtil.error("Tile Not Found", null));
    }



    @GetMapping
        public ResponseEntity<ApiResponse<List<Tile>>> getAllTiles() {
        return ResponseEntity.ok(ResponseUtil.success("Tiles Fetched", tileService.getAllTiles(), null));
    }

    @GetMapping("/table-details")
    public ResponseEntity<ApiResponse<List<TileTableDto>>> getAllTilesTableDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            @RequestParam(name = "sortBy", defaultValue = "_id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "search", defaultValue = "") String search

    ) {
        Page<TileTableDto> tileTableDtos = tileService.getAllTilesTableDetails(page, size, sortBy, sortDirection, search);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("pageable", tileTableDtos.getPageable());
        metadata.put("totalElements", tileTableDtos.getTotalElements());
        metadata.put("totalPages", tileTableDtos.getTotalPages());
        metadata.put("isFirst", tileTableDtos.isFirst());
        metadata.put("isLast", tileTableDtos.isLast());
        metadata.put("size", tileTableDtos.getSize());
        metadata.put("number", tileTableDtos.getNumber());
        metadata.put("numberOfElements", tileTableDtos.getNumberOfElements());
        metadata.put("sort", tileTableDtos.getSort());

        return ResponseEntity.ok(ResponseUtil.success("Tiles Fetched", tileTableDtos.getContent(), metadata));

    }

    @GetMapping("/tile-detail/{id}")
    public ResponseEntity<ApiResponse<TileDetailDto>> getTileDetail(@PathVariable String id) {
        TileDetailDto tile = tileService.getTileDetailById(id);
        return tile != null ?
                ResponseEntity.ok(ResponseUtil.success("Tile Fetched", tile, null)) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ResponseUtil.error("Tile Not Found", null));
    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<List<TileQtyDto>>> searchTiles(
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "location", defaultValue = "")DamageLocation location,
            @RequestParam(name = "givenId", defaultValue = "") String givenId
            ) {
        List<TileQtyDto> tileQtyDtos = tileService.searchTiles(search, location, givenId);
        return ResponseEntity.ok(ResponseUtil.success("Tiles fetched", tileQtyDtos, null));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Tile>> updateTile(@PathVariable String id, @RequestBody Tile tile) {
        tile.set_id(id);
        Tile updatedTile = tileService.updateTile(tile);
        return ResponseEntity.ok(ResponseUtil.success("Tile Updated Successfully", updatedTile, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTile(@PathVariable String id) {
        tileService.deleteTile(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(ResponseUtil.success("Deleted tile with Id: " + id, null, null));

    }
}
