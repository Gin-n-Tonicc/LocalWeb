package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
    Optional<Organisation> findByEmail(String email);

    List<Organisation> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM Organisation c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Organisation> findByIdAndDeletedAtIsNull(UUID id);
}