package org.folio.sample.integration.api.health;

import static org.hamcrest.Matchers.is;

import org.folio.sample.integration.AbstractBaseApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * A good example of a test class within the application.
 * Note that the endpoint being tested is not implemented in any controller but instead built into Spring Boot.
 *
 * The only Okapi requirement for /admin/health is to respond with a 200 status code.
 * @see https://wiki.folio.org/display/DD/Back+End+Module+Health+Check+Protocol
 */
class OkapiHealthTest extends AbstractBaseApiTest {

  @Test
  void testOkStatusReport() {
    ra()
      .get(getRequestUrl("admin/health/"))
      .then()
      .statusCode(is(HttpStatus.OK.value()));
  }
}
