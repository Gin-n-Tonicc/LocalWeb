package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    public TokenType tokenType;

    @Column(nullable = false)
    public boolean revoked;

    @Column(nullable = false)
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    public User user;
}
