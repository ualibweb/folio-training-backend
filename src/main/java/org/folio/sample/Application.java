package org.folio.sample;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class
 */
@SpringBootApplication
public class Application {

  /**
   * Run the Spring Boot application
   *
   * @param args command line arguments
   */
  // @Generated needed to remove from code coverage tests
  @Generated
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
