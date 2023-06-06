package org.folio.sample.integration.api.books;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;

import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.integration.AbstractBaseApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;



class BookAvailableTest extends AbstractBaseApiTest {

  @Test
  void testEmptyGet() {
    Response response = ra()
      .get(getRequestUrl("books/available"));

    response.then().statusCode(is(HttpStatus.OK.value()));

    // Assert the list is empty
    List<BookDTO> collection = response
      .getBody()
      .as(new TypeRef<List<BookDTO>>() {});
    assertThat(collection, hasSize(0));
  }

  @Test
  void getAllAvailableBooks() {
    ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("Book 1")
          .publishedDate(LocalDate.of(2000, 1, 1))
          .build()
      )
      .post(getRequestUrl("books"))
      .then()
      .statusCode(is(HttpStatus.CREATED.value()));

    ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("Book 2")
          .publishedDate(LocalDate.of(2000, 1, 1))
          .build()
      )
      .post(getRequestUrl("books"))
      .then()
      .statusCode(is(HttpStatus.CREATED.value()));


    Response response = ra()
      .get(getRequestUrl("books/available"));
    response.then().statusCode(is(HttpStatus.OK.value()));

    List<BookDTO> collection = response
      .getBody()
      .as(new TypeRef<List<BookDTO>>() {});
    assertThat(collection, hasSize(2));
    assertThat(collection.get(0).getName(), is(equalTo("Book 1")));
    assertThat(
      collection.get(0).getPublishedDate(),
      is(equalTo(LocalDate.of(2000, 1, 1)))
    );
  }


}