package org.folio.sample.integration.api.books;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.UUID;
import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.integration.AbstractBaseApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GetSingleBookTest extends AbstractBaseApiTest {

  @Test
  void testNotFound() {
    ra()
      .pathParam("id", UUID.fromString("00000000-0000-0000-0000-000000000000"))
      .get(getRequestUrl("books/{id}"))
      .then()
      .statusCode(is(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  void testGetWithBook() {
    Response postResponse = ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("Book 1")
          .publishedDate(LocalDate.of(2000, 1, 1))
          .build()
      )
      .post(getRequestUrl("books"));
    postResponse.then().statusCode(is(HttpStatus.CREATED.value()));

    UUID createdId = postResponse.as(BookDTO.class).getId();

    Response getResponse = ra()
      .pathParam("id", createdId)
      .get(getRequestUrl("books/{id}"));
    getResponse.then().statusCode(is(HttpStatus.OK.value()));

    BookDTO book = getResponse.getBody().as(BookDTO.class);
    assertThat(book.getName(), is(equalTo("Book 1")));
    assertThat(book.getPublishedDate(), is(equalTo(LocalDate.of(2000, 1, 1))));
  }
}
