package kz.bitlab.academy.internetmarket.comments.service;

import kz.bitlab.academy.internetmarket.comments.dto.CommentDTO;
import kz.bitlab.academy.internetmarket.comments.dto.EditCommentDTO;
import kz.bitlab.academy.internetmarket.comments.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    void create(CommentDTO commentDTO);
    String update(Long id, EditCommentDTO editCommentDTO);
    List<CommentEntity> findAllByItemId(Long itemId);
    CommentEntity findById(Long id);
    void delete(Long id);
    void deleteByItemId(Long itemId);

}
