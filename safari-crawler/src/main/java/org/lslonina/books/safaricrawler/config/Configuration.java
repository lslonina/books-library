package org.lslonina.books.safaricrawler.config;

import org.lslonina.books.safaricrawler.crawler.Crawler;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookDetailsRepository;
import org.lslonina.books.safaricrawler.repository.SafariBookRepository;
import org.lslonina.books.safaricrawler.service.BookService;
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

@SpringBootConfiguration
@EnableWebMvc
public class Configuration implements WebMvcConfigurer{
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
    public Crawler crawler(RestTemplate restTemplate, SafariBookRepository safariBookRepository, SafariBookDetailsRepository safariBookDetailsRepository, BookRepository bookRepository) throws IOException {
        return new Crawler(restTemplate, safariBookRepository, safariBookDetailsRepository, bookRepository);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }

    private static String readCookies() throws IOException {
        InputStream resourceAsStream = Configuration.class.getResourceAsStream("/cookie.txt");
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

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**");
//    }
}
