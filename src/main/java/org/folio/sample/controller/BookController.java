package org.folio.sample.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.domain.entity.Book;
import org.folio.sample.domain.mapper.BookMapper;
import org.folio.sample.rest.resource.BooksApi;
import org.folio.sample.service.BookService;
import org.folio.spring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main API controller
 */
@Log4j2
@RestController
@RequestMapping(value = "/")
public class BookController implements BooksApi {

  @Autowired
  private BookService bookService;

  @Autowired
  private BookMapper bookMapper;

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<List<BookDTO>> getAllBooks(Boolean onlyLeapYears) {
    log.info("Called GET /books with onlyLeapYears={}", onlyLeapYears);

    if (Boolean.TRUE.equals(onlyLeapYears)) {
      return new ResponseEntity<>(
          bookService
              .getAllBooksPublishedInLeapYears()
              .stream()
              .map(bookMapper::toDto)
              .toList(),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(
          bookService.getAllBooks().stream().map(bookMapper::toDto).toList(),
          HttpStatus.OK);
    }
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<BookDTO> createBook(BookForCreationDTO book) {
    log.info("Called POST /books with book={}", book);

    return new ResponseEntity<>(
        bookMapper.toDto(bookService.createBook(bookMapper.fromDto(book))),
        HttpStatus.CREATED);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<BookDTO> getBook(UUID bookId) {
    log.info("Called GET /books/{}", bookId);

    return new ResponseEntity<>(
        bookMapper.toDto(
            bookService
                .getBookById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"))),
        HttpStatus.OK);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<BookDTO> updateBook(UUID bookId, BookForCreationDTO book) {
    log.info("Called PUT /books/{}", bookId);

    // Get params for new book
    String newBookName = book.getName();
    LocalDate newBookPublishedDate = book.getPublishedDate();

    // Get old book, throw exception if not found and respond 404
    BookDTO oldBook = bookMapper.toDto(
        bookService
            .getBookById(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found")));
            
    // Get old book's id
    UUID oldBookId = oldBook.getId();

    // Update book, call updateBook method from BookService
    return new ResponseEntity<>(
        bookMapper.toDto(
            bookService.updateBook(oldBookId, newBookName, newBookPublishedDate)),
        HttpStatus.OK);
  }
}
