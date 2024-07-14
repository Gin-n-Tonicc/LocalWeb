package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM Lesson c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Lesson> findByIdAndDeletedAtIsNull(UUID id);

    List<Lesson> findAllByOrganisationIdAndDeletedAtIsNull(UUID id);
}
