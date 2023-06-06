package org.folio.sample.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "books")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {



  /**
   * The UUID of the book
   */
  @Id
  @NotNull
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  /**
   * The book's name
   */
  @NotNull
  @Column(name = "name")
  private String name;

  /**
   * When the book was published
   */
  @NotNull
  @Column(name = "published_date")
  private LocalDate publishedDate;

  /**
   * The book's availability
   */
  @NotNull
  @Column(name = "available")
  private boolean available;

  
  public boolean isPublishedInLeapYear() {
    if (this.getPublishedDate().getYear() % 4 != 0) {
      return false;
    }

    return (
      (publishedDate.getYear() % 100 != 0) ||
      (publishedDate.getYear() % 400 == 0)
    );
  }
}
