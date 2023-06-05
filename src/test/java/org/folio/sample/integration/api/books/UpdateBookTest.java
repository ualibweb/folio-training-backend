package org.folio.sample.integration.api.books;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import java.time.LocalDate;
import java.util.UUID;
import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.integration.AbstractBaseApiTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

class UpdateBookTest extends AbstractBaseApiTest {

  @MethodSource
  // localdate is optional and deffault value is 2000-01-01
  Response createBook(String name) {
    Response postResponse = ra()
      .body(
        BookForCreationDTO
          .builder()
          .name(name)
          .publishedDate(LocalDate.of(2000, 1, 1))
          .build()
      )
      .post(getRequestUrl("books"));
    return postResponse;
  }

  @Test
  void testNotFound() {
    ra()
      .pathParam("id", UUID.fromString("00000000-0000-0000-0000-000000000000"))
      .get(getRequestUrl("books/{id}"))
      .then()
      .statusCode(is(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  void updateBook() {
    // Create book
    Response postResponse = createBook("Book 1");
    postResponse.then().statusCode(is(HttpStatus.CREATED.value()));

    // Get ID of created book
    UUID createdId = postResponse.as(BookDTO.class).getId();

    // Create JSON object with updated information
    JSONObject putParams = new JSONObject();
    putParams.put("name", "Book 1 Updated");
    putParams.put("publishedDate", "2000-01-01");

    // Send PUT request with updated information
    Response putResponse = ra()
      .pathParam("id", createdId)
        .body(putParams)
      .put(getRequestUrl("books/{id}"));

    // Get the updated book
    Response getResponse = ra()
      .pathParam("id", createdId)
      .get(getRequestUrl("books/{id}"));

    // Check that the book was updated
    BookDTO book = getResponse.getBody().as(BookDTO.class);
    assertThat(book.getName(), is(equalTo("Book 1 Updated")));
  }

  @Test
  void updateBookBadId() {
    // Create book
    Response postResponse = createBook("Book 1");
    postResponse.then().statusCode(is(HttpStatus.CREATED.value()));

    // Get ID of created book and modify it so its invalid
    UUID createdId = UUID.fromString("00000000-0000-0000-0000-000000000000");

    // Create JSON object with updated information
    JSONObject putParams = new JSONObject();
    putParams.put("name", "Book 1 Updated");
    putParams.put("publishedDate", "2000-01-01");

    // Send PUT request with invalid id, should return 404 and assert equals that
    Response putResponse = ra()
        .pathParam("id", createdId)
            .body(putParams)
        .put(getRequestUrl("books/{id}"));
    putResponse.then().statusCode(is(HttpStatus.NOT_FOUND.value()));

    // Assert putResponse status code is 404
    assertThat(putResponse.getStatusCode(), is(equalTo(404)));

    }

}