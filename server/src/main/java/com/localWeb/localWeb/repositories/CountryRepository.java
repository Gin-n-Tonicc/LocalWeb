package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
    List<Country> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM Country c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Country> findByIdAndDeletedAtIsNull(UUID id);
}
