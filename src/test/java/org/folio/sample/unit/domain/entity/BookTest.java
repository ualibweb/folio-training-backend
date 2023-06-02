package org.folio.sample.unit.domain.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import org.folio.sample.domain.entity.Book;
import org.junit.jupiter.api.Test;

class BookTest {

  @Test
  void testLeapYear() {
    Book testBook = Book
      .builder()
      .name("Sample book")
      .publishedDate(LocalDate.of(2020, 1, 1))
      .build();
    assertThat(testBook.isPublishedInLeapYear(), is(true));
  }

  @Test
  void testNotLeapYear() {
    Book testBook = Book
      .builder()
      .name("Sample book")
      .publishedDate(LocalDate.of(2001, 1, 1))
      .build();
    assertThat(testBook.isPublishedInLeapYear(), is(false));
  }
}
