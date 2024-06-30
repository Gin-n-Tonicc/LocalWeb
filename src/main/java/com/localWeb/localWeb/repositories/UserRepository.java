package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findByEnabledFalseAndCreatedAtBeforeAndDeletedAtIsNull(LocalDateTime thresholdDateTime);
}