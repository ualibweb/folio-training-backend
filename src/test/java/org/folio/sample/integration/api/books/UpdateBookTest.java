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
import org.springframework.http.HttpStatus;

class UpdateBookTest extends AbstractBaseApiTest{
  @Test
  void testNotFound(){
    ra()
      .pathParam("id", UUID.fromString("00000000-0000-0000-0000-000000000000"))
      .get(getRequestUrl("books/{id}"))
      .then()
      .statusCode(is(HttpStatus.NOT_FOUND.value()));
  }

  @Test 
  void testUpdate(){
    Response postResponse = ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("book 1")
          .publishedDate(LocalDate.of(2024, 1, 1))
          .build()
      )
      .post(getRequestUrl("books"));
    postResponse.then().statusCode(is(HttpStatus.CREATED.value()));

    UUID createdId = postResponse.as(BookDTO.class).getId();

    JSONObject updateInfo = new JSONObject();

    updateInfo.put("name", "updated book");
    updateInfo.put("publishedDate", LocalDate.of(2024, 1, 1));
    updateInfo.put("isAvailable", true);

    Response putResponse = ra()
      .body(updateInfo)
      .pathParam("id", createdId)
      .put(getRequestUrl("books/{id}"));
    putResponse.then().statusCode(is(HttpStatus.OK.value()));
    
    Response getResponse = ra()
      .pathParam("id", createdId)
      .get(getRequestUrl("books/{id}"));
    getResponse.then().statusCode(is(HttpStatus.OK.value()));

    BookDTO book = getResponse.getBody().as(BookDTO.class);
    assertThat(book.getName(), is(equalTo("updated book")));
    assertThat(book.getPublishedDate(), is(equalTo(LocalDate.of(2024, 1, 1))));
  }
}
