package org.folio.sample.unit.domain.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import org.folio.sample.domain.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

class BookTest {

  @MethodSource
  void testIsPublishedInLeapYear(int year, boolean expected) {
    Book testBook = Book
      .builder()
      .name("Sample book")
      .publishedDate(LocalDate.of(year, 1, 1))
      .build();
    assertThat(testBook.isPublishedInLeapYear(), is(expected));
  }

  @Test
  void testLeapYear() {
    testIsPublishedInLeapYear(2020, true);
  }

  @Test
  void testNotLeapYear() {
    testIsPublishedInLeapYear(2001, false);
  }

  @Test
  void testNotLeapYear2() {
    testIsPublishedInLeapYear(99999, false);
  }

  @Test
  void testLeapYear2() {
    testIsPublishedInLeapYear(2000, true);
  }

  @Test
  void testLeapYear3() {
    testIsPublishedInLeapYear(1900, false);
  }

  @Test
  void testLeapYear4() {
    testIsPublishedInLeapYear(1, false);
  }
  
}