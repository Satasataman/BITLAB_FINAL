package kz.bitlab.academy.internetmarket.items.mapper;

import kz.bitlab.academy.internetmarket.items.dto.ItemEdit;
import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemEntity toEntity(ItemEdit input);

    ItemEntity toEntity(@MappingTarget ItemEntity entity, ItemEdit input);

}
