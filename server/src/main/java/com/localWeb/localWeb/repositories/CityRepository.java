package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM City c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<City> findByIdAndDeletedAtIsNull(UUID id);
}