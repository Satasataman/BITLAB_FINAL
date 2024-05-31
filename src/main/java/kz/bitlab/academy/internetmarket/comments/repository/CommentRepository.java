package kz.bitlab.academy.internetmarket.comments.repository;

import kz.bitlab.academy.internetmarket.comments.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByItemId(Long itemId);
    void deleteByItemId(Long itemId);
}
