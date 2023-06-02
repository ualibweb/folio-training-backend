package org.folio.sample.domain.mapper;

import javax.annotation.processing.Generated;
import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.domain.entity.Book;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-01T22:34:52-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Homebrew)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toDto(Book source) {
        if ( source == null ) {
            return null;
        }

        BookDTO.BookDTOBuilder bookDTO = BookDTO.builder();

        bookDTO.id( source.getId() );
        bookDTO.name( source.getName() );
        bookDTO.publishedDate( source.getPublishedDate() );

        return bookDTO.build();
    }

    @Override
    public Book fromDto(BookForCreationDTO source) {
        if ( source == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.name( source.getName() );
        book.publishedDate( source.getPublishedDate() );

        return book.build();
    }
}
