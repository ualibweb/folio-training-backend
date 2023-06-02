package org.folio.sample.repository;

import java.util.UUID;
import org.folio.sample.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A {@link org.springframework.data.jpa.repository.JpaRepository} of {@link org.folio.sample.domain.entity.Book} objects
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {}
