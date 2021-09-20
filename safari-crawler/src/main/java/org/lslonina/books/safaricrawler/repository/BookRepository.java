package org.lslonina.books.safaricrawler.repository;

import org.lslonina.books.safaricrawler.dto.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface BookRepository extends MongoRepository<Book, String> {
    Page<Book> findAllByPriorityEqualsAndLanguageEquals(int priority, String language, Pageable pageable);

    Page<Book> findAllByPriorityLessThanAndLanguageEquals(int priority, String language, Pageable pageable);

    Page<Book> findAllByPriorityGreaterThanAndLanguageEquals(int priority, String language, Pageable pageable);

    List<Book> findAllByIdentifierIn(Set<String> existingIds);
}
