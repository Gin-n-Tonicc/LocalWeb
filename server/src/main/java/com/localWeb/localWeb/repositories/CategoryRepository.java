package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Category> findByIdAndDeletedAtIsNull(UUID id);
}
