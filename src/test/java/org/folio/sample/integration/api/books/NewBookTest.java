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

class NewBookTest extends AbstractBaseApiTest {

  @Test
  void testNotFound() {
    ra()
      .pathParam("id", UUID.fromString("00000000-0000-0000-0000-000000000000"))
      .get(getRequestUrl("books/{id}"))
      .then()
      .statusCode(is(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  void testUpdateBook() {
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

    Response putResponse = ra()
        .body(
            BookForCreationDTO
            .builder()
            .name("new Book")
            .publishedDate(LocalDate.of(2002, 1, 1))
            .build()
        )   
      .pathParam("id", createdId)
      .put(getRequestUrl("books/{id}"));
    putResponse.then().statusCode(is(HttpStatus.OK.value()));

    BookDTO book = putResponse.getBody().as(BookDTO.class);
    assertThat(book.getName(), is(equalTo("new Book")));
    assertThat(book.getPublishedDate(), is(equalTo(LocalDate.of(2002, 1, 1))));
  }
}
