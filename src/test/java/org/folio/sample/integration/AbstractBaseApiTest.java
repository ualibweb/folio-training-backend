package org.folio.sample.integration;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode;
import lombok.extern.log4j.Log4j2;
import org.folio.spring.FolioModuleMetadata;
import org.folio.spring.integration.XOkapiHeaders;
import org.folio.tenant.domain.dto.TenantAttributes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Base abstract class for testing APIs.  Contains centralized APIs for Rest Assured, database initialization, etc
 */
@Log4j2
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureEmbeddedDatabase(refresh = RefreshMode.NEVER)
@ContextConfiguration(initializers = { WireMockInitializer.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractBaseApiTest {

  public static final String TENANT_ID = "test";

  @Autowired
  protected FolioModuleMetadata metadata;

  @Value("${x-okapi-url}")
  protected String okapiUrl = null;

  @LocalServerPort
  protected Integer port;

  @Autowired
  protected WireMockServer wireMockServer;

  @AfterEach
  void resetWiremock() {
    this.wireMockServer.resetAll();
  }

  @BeforeEach
  public void createDatabase() {
    log.info("Creating database...");
    tenantInstall(new TenantAttributes().moduleTo("mod-training-sample"));
  }

  @AfterEach
  public void clearDatabase() {
    tenantInstall(new TenantAttributes().moduleFrom("mod-training-sample"));
    log.info("Removing database...");
  }

  public void tenantInstall(TenantAttributes tenantAttributes) {
    // "/_/tenant" is not in Swagger schema, therefore, validation must be disabled
    // the v2.0 API of /_/tenant requires a non-empty moduleTo; without this, the module will not be initialized properly or enabled
    // the string we use does not matter (as there will be no modules in the database)
    ra()
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .body(tenantAttributes)
      .post(getRequestUrl("_/tenant"))
      .then()
      .statusCode(both(greaterThanOrEqualTo(200)).and(lessThanOrEqualTo(299)));
  }

  /**
   * Create a RestAssured object with the proper headers for Okapi testing
   *
   * @return a @link {RequestSpecification} ready for .get/.post and other
   *         RestAssured library methods
   */
  public RequestSpecification ra() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    return RestAssured
      .given()
      .header(new Header(XOkapiHeaders.URL, okapiUrl))
      .header(new Header(XOkapiHeaders.TENANT, TENANT_ID))
      .contentType(ContentType.JSON);
  }

  /**
   * Fully qualify a URL for testing. For example, if the path is "hello", this
   * method may return something like "http://localhost:8103/hello".
   *
   * @param path The API route's path
   * @return fully qualified URL
   */
  public String getRequestUrl(String path) {
    return String.format("http://localhost:%d/%s", port, path);
  }
}
