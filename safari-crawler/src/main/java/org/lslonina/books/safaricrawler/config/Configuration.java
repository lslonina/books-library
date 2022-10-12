package org.lslonina.books.safaricrawler.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.lslonina.books.safaricrawler.CrawlerRunner;
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
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
@EnableWebMvc
public class Configuration implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    @Value("${crawler.user}")
    private String user;

    @Value("${crawler.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) throws IOException {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(24);
        connectionManager.setDefaultMaxPerRoute(12);

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setSocketTimeout(60000)
                .setConnectTimeout(60000).build();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user1", "user1Pass");
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        List<Header> headers = Arrays.asList(
                new BasicHeader(HttpHeaders.ACCEPT,
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"),
                new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"),
                new BasicHeader(HttpHeaders.REFERER, "https://learning.oreilly.com/login/unified/?next=/home/"),
                new BasicHeader("origin", "https://learning.oreilly.com"),
                new BasicHeader("upgrade-insecure-requests", "1"),
                new BasicHeader(HttpHeaders.USER_AGENT,
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"),
                new BasicHeader("cookie", readCookies()));

        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
                .setDefaultCredentialsProvider(credentialsProvider).setDefaultHeaders(headers)
                .setDefaultRequestConfig(requestConfig).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }

    @Bean
    public OreillyClient booksClient(RestTemplate restTemplate) {
        return new OreillyClient(restTemplate);
    }

    @Bean
    public OreillyBookService oreillyBookService(SafariBookRepository safariBookRepository,
            SafariBookDetailsRepository safariBookDetailsRepository) {
        return new OreillyBookService(safariBookRepository, safariBookDetailsRepository);
    }

    @Bean
    public Crawler crawler(OreillyClient booksClient, OreillyBookService oreillyBookService, BookService bookService,
            BooksProcessor bookProcessor) throws IOException {
        return new Crawler(booksClient, oreillyBookService, bookService, bookProcessor);
    }

    @Bean
    public CrawlerRunner crawlerRunner(Crawler crawler) {
        return new CrawlerRunner(crawler);
    }

    @Bean
    public BooksProcessor booksProcessor(OreillyBookService oreillyBookService, BookRepository bookRepository,
            BookFactory bookFactory) {
        return new BooksProcessor(oreillyBookService, bookRepository, bookFactory);
    }

    @Bean
    BookFactory bookFactory(Map<String, Integer> processedBooks) {
        return new BookFactory(processedBooks);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }

    @Bean
    public Map<String, Integer> processedBooks() {
        try {
            Map<String, Integer> processed = Files.readAllLines(Path.of("D:\\data\\books\\ignored.csv")).stream()
                    .map(s -> s.split(",")).collect(Collectors.toMap(a -> a[0], a -> Integer.valueOf(a[1])));
            processed.putAll(Files.readAllLines(Path.of("D:\\data\\books\\selected.csv")).stream()
                    .map(s -> s.split(",")).collect(Collectors.toMap(a -> a[0], a -> Integer.valueOf(a[1]))));
            return processed;
        } catch (IOException ex) {
            log.warn("No processed books found");
            return Collections.emptyMap();
        }
    }

    private static String readCookies() {
        try (InputStream resourceAsStream = Configuration.class.getResourceAsStream("/cookie.txt")) {
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(
                    new InputStreamReader(resourceAsStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            return textBuilder.toString();
        } catch (IOException ex) {
            log.error("Can't load cookies");
            return "";
        }
    }

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry.addMapping("/**");
    // }
}
