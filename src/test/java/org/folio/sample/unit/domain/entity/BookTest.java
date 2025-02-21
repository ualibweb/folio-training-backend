package org.folio.sample.unit.domain.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.folio.sample.domain.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BookTest {

  static Stream<Arguments> leapYearTestCases() {
        return Stream.of(
            Arguments.of(LocalDate.of(2020, 1, 1), true),
            Arguments.of(LocalDate.of(2001, 1, 1), false),
            Arguments.of(LocalDate.of(2000, 1, 1), true),
            Arguments.of(LocalDate.of(1900, 1, 1), false)
        );
    }

    @ParameterizedTest
    @MethodSource("leapYearTestCases")
    void testIsPublishedInLeapYear(LocalDate publishedDate, boolean expected) {
        Book testBook = Book.builder()
            .name("Sample book")
            .publishedDate(publishedDate)
            .build();
        
        assertThat(testBook.isPublishedInLeapYear(), is(expected));
    }
}
