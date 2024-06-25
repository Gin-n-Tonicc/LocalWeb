package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "permissions")
public class Permission extends BaseEntity {
    private String name;
    private String url;
    @ManyToMany(mappedBy = "permissions")
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();
}
