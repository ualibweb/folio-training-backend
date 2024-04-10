package org.folio.sample.integration.api.books;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.folio.sample.domain.dto.BookDTO;
import org.folio.sample.domain.dto.BookForCreationDTO;
import org.folio.sample.integration.AbstractBaseApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GetAllAvailableTest extends AbstractBaseApiTest {
  
  @Test
  void testEmptyGet(){
    ra()
      .get(getRequestUrl("books/available"))
      .then()
      .statusCode(is(HttpStatus.OK.value()));
    
    Response response = ra()
    .get(getRequestUrl("books/available"));
    response.then().statusCode(is(HttpStatus.OK.value()));

    List<BookDTO> collection = response
      .body()
      .as(new TypeRef<List<BookDTO>>() {});
    assertThat(collection, hasSize(0));
  }

  @Test
  void testGetWithBooks(){
    ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("book 1")
          .publishedDate(LocalDate.of(2020, 1, 1))
          .isAvailable(true)
          .build()
      )
      .post(getRequestUrl("books"))
      .then()
      .statusCode(is(HttpStatus.CREATED.value()));

    ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("book 2")
          .publishedDate(LocalDate.of(2020, 1, 1))
          .isAvailable(false)
          .build()
      )
      .post(getRequestUrl("books"))
      .then()
      .statusCode(is(HttpStatus.CREATED.value()));

    Response response = ra()
      .get(getRequestUrl("books/available"));
    response.then().statusCode(is(HttpStatus.OK.value()));

    List<BookDTO> collection = response
      .body()
      .as(new TypeRef<List<BookDTO>>() {});
    assertThat(collection, hasSize(1));
    assertThat(collection.get(0).getName(), is(equalTo("book 1")));
  }
}
