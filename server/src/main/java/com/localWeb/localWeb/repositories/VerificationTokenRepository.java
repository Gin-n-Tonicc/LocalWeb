package com.localWeb.localWeb.repositories;

import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.models.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    List<VerificationToken> findByUserAndCreatedAtBefore(User user, LocalDateTime thresholdDateTime);

}
