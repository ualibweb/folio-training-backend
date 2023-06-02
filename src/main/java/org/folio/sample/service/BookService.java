package org.folio.sample.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.folio.sample.domain.entity.Book;
import org.folio.sample.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BookService {

  private final BookRepository bookRepository;

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public List<Book> getAllBooksPublishedInLeapYears() {
    return getAllBooks().stream().filter(Book::isPublishedInLeapYear).toList();
  }

  // we use Optional since passing around nulls is a bad practice
  public Optional<Book> getBookById(UUID id) {
    return bookRepository.findById(id);
  }

  public Book createBook(Book book) {
    return bookRepository.save(book);
  }
}
