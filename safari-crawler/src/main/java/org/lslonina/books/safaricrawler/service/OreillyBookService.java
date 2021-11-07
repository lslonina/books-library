package org.lslonina.books.safaricrawler.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.lslonina.books.safaricrawler.model.details.SafariBookDetails;
import org.lslonina.books.safaricrawler.model.generic.SafariBook;
import org.lslonina.books.safaricrawler.repository.SafariBookDetailsRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OreillyBookService {
    private static final Logger log = LoggerFactory.getLogger(OreillyBookService.class);

    private final SafariBookRepository safariBookRepository;
    private final SafariBookDetailsRepository safariBookDetailsRepository;

    public OreillyBookService(SafariBookRepository safariBookRepository,
            SafariBookDetailsRepository safariBookDetailsRepository) {
        this.safariBookRepository = safariBookRepository;
        this.safariBookDetailsRepository = safariBookDetailsRepository;
    }

    public Collection<SafariBook> findAllBooksByIdentifierIn(Set<String> ids) {
        return safariBookRepository.findAllByArchiveIdIn(ids);
    }

    public Collection<SafariBookDetails> findAllBooksDetailsByIdentifierIn(Set<String> ids) {
        return safariBookDetailsRepository.findAllByIdentifierIn(ids);
    }

    public void saveBooks(Collection<SafariBook> safariBooks) {
        safariBookRepository.saveAll(safariBooks);
    }

    public void saveBooksDetails(List<SafariBookDetails> booksDetails) {
        try {
            safariBookDetailsRepository.saveAll(booksDetails);
        } catch (Exception e) {
            log.error("Can't store at once", e);
            for (SafariBookDetails bookDetail : booksDetails) {
                try {
                    safariBookDetailsRepository.save(bookDetail);
                } catch (RuntimeException ex) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String json = mapper.writeValueAsString(bookDetail);
                        log.error("Error storing: " + json);
                    } catch (JsonProcessingException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }

    }
}
