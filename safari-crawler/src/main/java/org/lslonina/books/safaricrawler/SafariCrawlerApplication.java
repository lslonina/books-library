package org.lslonina.books.safaricrawler;

import org.lslonina.books.safaricrawler.crawler.Crawler;
import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookDetailsRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class SafariCrawlerApplication {
    private static final Logger log = LoggerFactory.getLogger(SafariCrawlerApplication.class);
    private static final String ID_PREFIX = "https://www.safaribooksonline.com/api/v1/book/";

    public static void main(String[] args) {
        SpringApplication.run(SafariCrawlerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(SafariBookRepository safariBookRepository, SafariBookDetailsRepository bookDetailsRepository, BookRepository bookRepository, Crawler crawler) throws Exception {
        return args -> {
//            export(bookRepository);
//            deleteData(safariBookRepository, bookDetailsRepository, bookRepository);
            fetchData(safariBookRepository, bookDetailsRepository, bookRepository, crawler);
        };
    }

    private void fetchData(SafariBookRepository safariBookRepository, SafariBookDetailsRepository bookDetailsRepository, BookRepository bookRepository, Crawler crawler) {
        try {
            crawler.loadData();
        } catch (RuntimeException ex) {
            log.info("Can't load data", ex);
        }
    }

    private void export(BookRepository bookRepository) {
        List<Book> all = bookRepository.findAll();
        List<Book> selected = all.stream().filter(b -> b.getPriority() > 0).collect(Collectors.toList());
        List<Book> ignored = all.stream().filter(b -> b.getPriority() < 0).collect(Collectors.toList());

        export(selected, "selected");
        export(ignored, "ignored");
        System.out.println("Exported.");
    }

    private void export(List<Book> books, String prefix) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        String id = books.stream().map(book -> book.getIdentifier()).collect(Collectors.joining("\n"));
        try {
            Files.write(Path.of(prefix + "-id-" + strDate + ".csv"), id.trim().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Wasn't able to store file: " + prefix, e);
        }

    }

    private void deleteData(SafariBookRepository safariBookRepository, SafariBookDetailsRepository safariBookDetailsRepository, BookRepository bookRepository) {
        safariBookDetailsRepository.deleteAll();
        safariBookRepository.deleteAll();
        bookRepository.deleteAll();
        System.out.println("Deleted.");
    }
}