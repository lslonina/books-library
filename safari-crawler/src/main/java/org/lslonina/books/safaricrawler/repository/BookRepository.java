package org.lslonina.books.safaricrawler.repository;

import java.util.List;
import java.util.Set;

import org.lslonina.books.safaricrawler.dto.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Meta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

    @Meta(allowDiskUse = true)
    Page<Book> findAllByPriorityEqualsAndLanguageEquals(int priority, String language, Pageable pageable);

    @Meta(allowDiskUse = true)
    Page<Book> findAllByPriorityLessThanAndLanguageEquals(int priority, String language, Pageable pageable);

    @Meta(allowDiskUse = true)
    List<Book> findAllByPriorityLessThanAndLanguageEquals(int priority, String language);

    @Meta(allowDiskUse = true)
    Page<Book> findAllByPriorityGreaterThanAndLanguageEquals(int priority, String language, Pageable pageable);

    @Meta(allowDiskUse = true)
    List<Book> findAllByPriorityGreaterThanAndLanguageEquals(int priority, String language);

    @Meta(allowDiskUse = true)
    List<Book> findAllByIdentifierIn(Set<String> existingIds);
}
