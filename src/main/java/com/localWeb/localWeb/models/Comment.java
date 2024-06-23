package com.localWeb.localWeb.models;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "The owner of the comment should not be null!")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @NotNull(message = "The post of the comment should not be null!")
    private Post post;

    @NotNull(message = "The text of the comment should not be null!")
    @Size(min = 30, max = 200, message = "The text of the comment should be between 30 and 200 symbols!")
    private String text;

    private int likeCount;
}
