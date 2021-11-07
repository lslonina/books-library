package org.lslonina.books.safaricrawler.config;

import org.lslonina.books.safaricrawler.crawler.BookFactory;
import org.lslonina.books.safaricrawler.crawler.BooksProcessor;
import org.lslonina.books.safaricrawler.crawler.Crawler;
import org.lslonina.books.safaricrawler.crawler.OreillyClient;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookDetailsRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookRepository;
import org.lslonina.books.safaricrawler.service.BookService;
import org.lslonina.books.safaricrawler.service.OreillyBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootConfiguration
@EnableWebMvc
public class Configuration implements WebMvcConfigurer{
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    @Value("${crawler.user}")
    private String user;

    @Value("${crawler.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) throws IOException {
        return restTemplateBuilder
                .basicAuthentication(user, password)
                .defaultHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .defaultHeader("accept-encoding", "gzip, deflate")
                .defaultHeader("origin", "https://learning.oreilly.com")
                .defaultHeader("referer", "https://learning.oreilly.com/login/unified/?next=/home/")
                .defaultHeader("upgrade-insecure-requests", "1")
                .defaultHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                .defaultHeader("cookie", readCookies())
                .build();
    }

    @Bean
    public OreillyClient booksClient(RestTemplate restTemplate) {
        return new OreillyClient(restTemplate);
    }

    @Bean
    public OreillyBookService oreillyBookService(SafariBookRepository safariBookRepository, SafariBookDetailsRepository safariBookDetailsRepository) {
        return new OreillyBookService(safariBookRepository, safariBookDetailsRepository);
    }

    @Bean
    public Crawler crawler(OreillyClient booksClient, OreillyBookService bookService, BooksProcessor bookProcessor) throws IOException {
        return new Crawler(booksClient, bookService, bookProcessor);
    }

    @Bean
    public BooksProcessor booksProcessor(OreillyBookService oreillyBookService, BookRepository bookRepository, BookFactory bookFactory) {
        return new BooksProcessor(oreillyBookService, bookRepository, bookFactory);
    }

    @Bean BookFactory bookFactory(Map<String, Integer> processedBooks) {
        return new BookFactory(processedBooks);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }

    @Bean
    public Map<String, Integer> processedBooks() {
        try {
            Map<String, Integer> processed = Files.readAllLines(Path.of("D:\\data\\books\\ignored.csv")).stream().map(s -> s.split(",")).collect(Collectors.toMap(a -> a[0], a -> Integer.valueOf(a[1])));
            processed.putAll(Files.readAllLines(Path.of("D:\\data\\books\\selected.csv")).stream().map(s -> s.split(",")).collect(Collectors.toMap(a -> a[0], a -> Integer.valueOf(a[1]))));
            return processed;
        }
        catch (IOException ex) {
            log.warn("No processed books found");
            return Collections.emptyMap();
        }
    }

    private static String readCookies() {
        try (InputStream resourceAsStream = Configuration.class.getResourceAsStream("/cookie.txt")) {
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (resourceAsStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            return textBuilder.toString();
        } 
        catch (IOException ex) {
            log.error("Can't load cookies");
            return "";
        }
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**");
//    }
}
