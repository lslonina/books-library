package org.lslonina.books.safaricrawler.repository;

import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface SafariBookDetailsRepository extends MongoRepository<SafariBookDetails, String> {
    List<SafariBookDetails> findAllByIdentifierIn(Set<String> ids);

}