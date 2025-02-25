package com.trustrace.tiles_hub_be.controllers;

import com.trustrace.tiles_hub_be.model.collections.tile.Tile;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.service.TileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
