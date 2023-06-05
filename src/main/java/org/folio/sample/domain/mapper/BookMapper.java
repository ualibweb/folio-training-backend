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

  BookDTO toDto(Book source);

  @Mapping(target = "id", ignore = true)
  Book fromDto(BookForCreationDTO source);

BookDTO bookForCreationDTOToBookDTO(BookForCreationDTO bookForCreationDTO);
}
