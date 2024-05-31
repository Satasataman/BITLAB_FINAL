package kz.bitlab.academy.internetmarket.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditCommentDTO {

    private Long id;
    private String newText;

}
