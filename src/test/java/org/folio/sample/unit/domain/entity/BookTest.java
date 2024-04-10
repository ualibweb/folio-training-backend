package org.folio.sample.unit.domain.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.folio.sample.domain.entity.Book;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;


class BookTest {

  @ParameterizedTest
  @MethodSource("datesStream")
  void testIsLeapYear (int year, Boolean result) {
    Book testBook = Book
      .builder()
      .name("Sample book")
      .publishedDate(LocalDate.of(year, 1, 1))
      .build();
    assertThat(testBook.isPublishedInLeapYear(), is(result));
  }

  private static Stream<Arguments> datesStream() {
    return Stream.of(
      Arguments.of(2020, true),
      Arguments.of(2001, false),
      Arguments.of(2100, false),
      Arguments.of(2400, true)
    );
  }
}
