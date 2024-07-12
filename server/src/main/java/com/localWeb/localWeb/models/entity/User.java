package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.enums.Provider;
import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @NotNull(message = "The name of the user should not be null!")
    @Size(min = 2, message = "The name should be at least 2 symbols!")
    private String name;

    @NotNull(message = "The surname should not be null!")
    @Size(min = 2, message = "The surname should be at least 2 symbols!")
    private String surname;

    @Email(message = "Email should be a well-formatted email!")
    @NotNull(message = "The email should not be null!")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Required minimum eight characters, one letter & one number!")
    private String password;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File avatar;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(mappedBy = "members")
    @ToString.Exclude
    private Set<Organisation> memberOrganisations = new HashSet<>();

    @ManyToMany(mappedBy = "owners")
    private Set<Organisation> organisations = new HashSet<>();

    @OneToMany(mappedBy = "phoneable", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Phone> phones = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_lesson",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    @ToString.Exclude
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @ToString.Exclude
    private Set<Group> groups = new HashSet<>();

    @OneToMany
    private Set<File> files = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "is_additional_info_required", nullable = false)
    private boolean additionalInfoRequired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @PrePersist
    void prePersist() {
        if (this.provider == null) {
            this.provider = Provider.LOCAL;
        }
    }
}

