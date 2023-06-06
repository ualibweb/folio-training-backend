package org.folio.sample.domain.mapper;

import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.domain.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  @Mapping(source="available", target="isAvailable")
  BookDTO toDto(Book source);

  @Mapping(target = "id", ignore = true)
  @Mapping(source="isAvailable", target="available")
  Book fromDto(BookForCreationDTO source);
}