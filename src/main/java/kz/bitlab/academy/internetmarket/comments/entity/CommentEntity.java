package kz.bitlab.academy.internetmarket.comments.entity;

import jakarta.persistence.*;
import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import kz.bitlab.academy.internetmarket.users.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    public CommentEntity(String comment, ItemEntity item, UserEntity user) {
        this.comment = comment;
        this.item = item;
        this.user = user;
    }

}
