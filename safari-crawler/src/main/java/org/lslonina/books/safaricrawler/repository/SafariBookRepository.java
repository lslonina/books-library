package org.lslonina.books.safaricrawler.repository;

import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface SafariBookRepository extends MongoRepository<SafariBook, String> {
    List<SafariBook> findAllByArchiveIdIn(Set<String> ids);
}