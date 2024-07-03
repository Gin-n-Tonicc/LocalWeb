package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByIdAndDeletedAtIsNull(UUID id);
}
