package kz.bitlab.academy.internetmarket.comments.controller;

import kz.bitlab.academy.internetmarket.comments.dto.CommentDTO;
import kz.bitlab.academy.internetmarket.comments.dto.EditCommentDTO;
import kz.bitlab.academy.internetmarket.comments.repository.CommentRepository;
import kz.bitlab.academy.internetmarket.comments.service.CommentService;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/details/{id}/addComment")
    public String createComment(@PathVariable Long id,
                                CommentDTO commentDTO) {

        commentService.create(commentDTO);

        return "redirect:/details/" + id;
    }

    @PostMapping("/details/updateComment")
    public String updateComment(@RequestParam(name = "itemId") Long itemId,
                                EditCommentDTO editCommentDTO) {

        commentService.update(editCommentDTO.getId(), editCommentDTO);

        return "redirect:/details/" + itemId;
    }

    @PostMapping("/details/deleteComment")
    public String deleteComment(@RequestParam(name = "itemId") Long itemId,
                                @RequestParam(name = "commentId") Long commentId) {

        commentService.delete(commentId);

        return "redirect:/details/" + itemId;
    }

}
