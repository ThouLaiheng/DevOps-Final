package com.fieldrental.controller;

import com.fieldrental.model.Terrain;
import com.fieldrental.repository.TerrainRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/terrains")
public class TerrainController {

    private final TerrainRepository terrainRepository;

    public TerrainController(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    @GetMapping
    public List<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terrain> getTerrainById(@PathVariable Long id) {
        return terrainRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    public List<Terrain> getTerrainsByOwner(@PathVariable Long ownerId) {
        return terrainRepository.findByOwnerId(ownerId);
    }

    @GetMapping("/available")
    public List<Terrain> getAvailableTerrains() {
        return terrainRepository.findByIsAvailableTrue();
    }

    @GetMapping("/search/location")
    public List<Terrain> searchByLocation(@RequestParam String q) {
        return terrainRepository.findByLocationContainingIgnoreCase(q);
    }

    @GetMapping("/search/title")
    public List<Terrain> searchByTitle(@RequestParam String q) {
        return terrainRepository.findByTitleContainingIgnoreCase(q);
    }

    @PostMapping
    public ResponseEntity<Terrain> createTerrain(@Valid @RequestBody Terrain terrain) {
        Terrain saved = terrainRepository.save(terrain);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Terrain> updateTerrain(@PathVariable Long id, @Valid @RequestBody Terrain terrain) {
        return terrainRepository.findById(id)
                .map(existing -> {
                    terrain.setId(id);
                    terrain.setCreatedAt(existing.getCreatedAt());
                    return ResponseEntity.ok(terrainRepository.save(terrain));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerrain(@PathVariable Long id) {
        if (terrainRepository.existsById(id)) {
            terrainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
