package kz.bitlab.academy.internetmarket.users.mapper;

import kz.bitlab.academy.internetmarket.users.dto.UserCreate;
import kz.bitlab.academy.internetmarket.users.dto.UserUpdate;
import kz.bitlab.academy.internetmarket.users.entity.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserCreate input);

    UserEntity toEntity(@MappingTarget UserEntity entity, UserUpdate input);

}
