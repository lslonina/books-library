package org.lslonina.books.safaricrawler;

import org.lslonina.books.safaricrawler.crawler.Crawler;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookDetailsRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookRepository;
import org.lslonina.books.safaricrawler.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafariCrawlerApplication {
    private static final Logger log = LoggerFactory.getLogger(SafariCrawlerApplication.class);
    private static final String ID_PREFIX = "https://www.safaribooksonline.com/api/v1/book/";

    public static void main(String[] args) {
        SpringApplication.run(SafariCrawlerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BookService bookService, Crawler crawler) throws Exception {
        return args -> {
            bookService.export();
            fetchData(crawler);
        };
    }

    private void fetchData(Crawler crawler) {
        try {
            crawler.loadData();
            // crawler.refreshCovers();
        } catch (RuntimeException ex) {
            log.info("Can't load data", ex);
        }
    }
}