package com.localWeb.localWeb.models.dto.auth;

import com.localWeb.localWeb.models.entity.Group;
import com.localWeb.localWeb.models.entity.Lesson;
import com.localWeb.localWeb.models.entity.Organisation;
import com.localWeb.localWeb.models.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDTO extends PublicUserDTO {
    private String lastname;
    private String address;
    private Set<Organisation> organisations = new HashSet<>();
    private Set<Lesson> lessons = new HashSet<>();
    private Set<Group> groups = new HashSet<>();
    private Set<Phone> phones = new HashSet<>();
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
