package com.fieldrental.repository;

import com.fieldrental.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    List<Favorite> findByTerrainId(Long terrainId);
    Optional<Favorite> findByUserIdAndTerrainId(Long userId, Long terrainId);
    boolean existsByUserIdAndTerrainId(Long userId, Long terrainId);
    @Transactional
    void deleteByUserIdAndTerrainId(Long userId, Long terrainId);
}
