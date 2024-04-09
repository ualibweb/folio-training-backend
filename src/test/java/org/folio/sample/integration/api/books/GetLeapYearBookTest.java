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

public class GetLeapYearBookTest extends AbstractBaseApiTest {
  @Test
  void testEmptyGet(){
    Response response = ra()
      .queryParam("onlyLeapYears", true)
      .get(getRequestUrl("books"));
    response.then().statusCode(is(HttpStatus.OK.value()));

    List<BookDTO> bookList = response
      .getBody()
      .as(new TypeRef<List<BookDTO>>() {});
    assertThat(bookList, hasSize(0));
  }

  @Test
  void testGetWithBook() {
    ra()
      .body(
        BookForCreationDTO
          .builder()
          .name("test book")
          .publishedDate(LocalDate.of(2020, 1, 1))
          .build()
      )
      .post(getRequestUrl("books"))
      .then()
      .statusCode(is(HttpStatus.CREATED.value()));
      
    Response response = ra()
      .queryParam("onlyLeap", true)
      .get(getRequestUrl("books"));
    response.then().statusCode(is(HttpStatus.OK.value()));

    List<BookDTO> bookList = response
      .body()
      .as(new TypeRef<List<BookDTO>>() {});
    
    assertThat(bookList, hasSize(1));
    assertThat(bookList.get(0).getName(), is(equalTo("test book")));
    assertThat(bookList.get(0).getPublishedDate(), is(equalTo(LocalDate.of(2020, 1, 1))));
  }
}
