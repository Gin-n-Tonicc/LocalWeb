package com.localWeb.localWeb.repositories;


import com.localWeb.localWeb.enums.ApplicationStatus;
import com.localWeb.localWeb.models.entity.Application;
import com.localWeb.localWeb.models.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findAllByDeletedAtIsNull();

    @Query("SELECT c FROM Application c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Application> findByIdAndDeletedAtIsNull(UUID id);

    List<Application> findByLessonAndStatus(Lesson lesson, ApplicationStatus status);

    List<Application> findAllByLessonIdAndDeletedAtIsNull(UUID id);
}
