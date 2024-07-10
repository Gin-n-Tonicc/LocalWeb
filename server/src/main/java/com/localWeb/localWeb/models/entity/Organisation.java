package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import com.localWeb.localWeb.utils.SlugUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "organisations")
public class Organisation extends BaseEntity {

    @NotNull(message = "The name should not be null!")
    private String name;

    private String slug;

    @NotNull(message = "The description should not be null!")
    @Size(min = 60, max = 400, message = "The description must be between 60 and 400 symbols!")
    private String description;

    @Email(message = "Email should be a well-formatted email!")
    @NotNull(message = "The email should not be null!")
    @Column(unique = true)
    private String email;

    private String websiteUrl;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File profileImage;

    @ManyToMany
    @JoinTable(
            name = "organisation_members",
            joinColumns = @JoinColumn(name = "organisation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<User> members = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "organisation_owners",
            joinColumns = @JoinColumn(name = "organisation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<User> owners = new HashSet<>();

    @OneToMany(mappedBy = "id")
    private Set<File> files = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void updateSlug() {
        this.slug = SlugUtils.generateSlug(this.name);
    }
}