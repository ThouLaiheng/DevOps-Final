package com.fieldrental.controller;

import com.fieldrental.model.Favorite;
import com.fieldrental.repository.FavoriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @GetMapping
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favorite> getFavoriteById(@PathVariable Long id) {
        return favoriteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Favorite> getFavoritesByUser(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @GetMapping("/terrain/{terrainId}")
    public List<Favorite> getFavoritesByTerrain(@PathVariable Long terrainId) {
        return favoriteRepository.findByTerrainId(terrainId);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFavorite(@RequestParam Long userId, @RequestParam Long terrainId) {
        boolean exists = favoriteRepository.existsByUserIdAndTerrainId(userId, terrainId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite favorite) {
        if (favoriteRepository.existsByUserIdAndTerrainId(favorite.getUserId(), favorite.getTerrainId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Favorite saved = favoriteRepository.save(favorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam Long terrainId) {
        if (favoriteRepository.existsByUserIdAndTerrainId(userId, terrainId)) {
            favoriteRepository.deleteByUserIdAndTerrainId(userId, terrainId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
