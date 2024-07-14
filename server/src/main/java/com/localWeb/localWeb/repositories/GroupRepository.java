package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM Group c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Group> findByIdAndDeletedAtIsNull(UUID id);

    List<Group> findAllByLessonIdAndDeletedAtIsNull(UUID id);

    List<Group> findAllByOwnerIdAndDeletedAtIsNull(UUID id);
}
