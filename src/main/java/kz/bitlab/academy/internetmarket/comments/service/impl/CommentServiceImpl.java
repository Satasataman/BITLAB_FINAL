package kz.bitlab.academy.internetmarket.comments.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.bitlab.academy.internetmarket.comments.dto.CommentDTO;
import kz.bitlab.academy.internetmarket.comments.dto.EditCommentDTO;
import kz.bitlab.academy.internetmarket.comments.entity.CommentEntity;
import kz.bitlab.academy.internetmarket.comments.repository.CommentRepository;
import kz.bitlab.academy.internetmarket.comments.service.CommentService;
import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import kz.bitlab.academy.internetmarket.items.repository.ItemRepository;
import kz.bitlab.academy.internetmarket.users.entity.UserEntity;
import kz.bitlab.academy.internetmarket.users.repository.UserRepository;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void create(CommentDTO commentDTO) {

        ItemEntity item = itemRepository.findById(commentDTO.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        UserEntity user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CommentEntity commentEntity = new CommentEntity(commentDTO.getText(), item, user);

        repository.save(commentEntity);
    }

    @Override
    public String update(Long id, EditCommentDTO editCommentDTO) {

        CommentEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));

        entity.setComment(editCommentDTO.getNewText());
        repository.save(entity);

        return "redirect:/details/" + entity.getItem().getId();
    }

    @Override
    public List<CommentEntity> findAllByItemId(Long itemId) {
        List<CommentEntity> comments = repository.findAllByItemId(itemId);
        Collections.sort(comments, Comparator.comparingLong(CommentEntity::getId).reversed());
        return comments;
    }
    @Transactional(readOnly = true)
    @Override
    public CommentEntity findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentary not found"));
    }

    @Transactional
    @Override
    public void delete(Long id) {

        if (Objects.equals(findById(id).getUser().getId(), userService.getCurrentUser().getId()) || userService.getCurrentUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            repository.deleteById(id);
        }
    }

    public void deleteByItemId(Long itemId) {
        repository.deleteByItemId(itemId);
    }
}
