package kz.bitlab.academy.internetmarket.comments.mapper;

import kz.bitlab.academy.internetmarket.comments.dto.CommentDTO;
import kz.bitlab.academy.internetmarket.comments.dto.EditCommentDTO;
import kz.bitlab.academy.internetmarket.comments.entity.CommentEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    CommentEntity toEntity(CommentDTO input);
    CommentEntity toEntity(@MappingTarget CommentEntity entity, CommentDTO input);
    void updateEntity(@MappingTarget CommentEntity entity, EditCommentDTO editDTO);

}
