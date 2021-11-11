package org.lslonina.books.safaricrawler.api;


import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class BookApi {
    private static final Logger log = LoggerFactory.getLogger(BookApi.class);

    private final BookService bookService;

    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> bookList(@RequestParam(required = false) String filter, @RequestParam(required = false) Integer page) {
        PageRequest pageRequest = PageRequest.of(page == null ? 0 : page, 100, Sort.by("published").descending());
        Page<Book> result;
        if (filter == null) {
            return bookService.findAllSelected().toList();
        } else if (filter.equals("all")) {
            result = bookService.findAll(pageRequest);
        } else if (filter.equals("skipped")) {
            PageRequest skippedPageRequest = PageRequest.of(page == null ? 0 : page, 100, Sort.by("modificationTimestamp").descending());
            result = bookService.findSkipped(skippedPageRequest);
        } else if (filter.equals("selected")) {
            pageRequest = PageRequest.of(page == null ? 0 : page, 100, Sort.by("published").descending());
            result = bookService.findSelected(pageRequest);
            Date date = Calendar.getInstance().getTime();
            List<Book> books = result.stream().filter(b -> b.getPublished().before(date)).collect(Collectors.toList());
            result = new PageImpl<Book>(books);
        } else if (filter.equals("unprocessed")) {
            result = bookService.findUnprocessed(pageRequest);
        } else {
            throw new RuntimeException("Query param not supported: " + filter);
        }
        log.info("Total: " + result.getTotalElements());
        return result.toList();
    }

    @GetMapping("/books/{id}")
    public Book one(@PathVariable String id) {
        if (!id.equals("undefined")) {
            return bookService.findById(id);
        }
        log.info("Undefined: " + id);
        return null;
    }

    @PostMapping("/books/{id}/skip")
    public void skip(@PathVariable String id) {
        if (!id.equals("undefined")) {
            bookService.skip(id);
        }
    }

    @PostMapping("/books/{id}/select")
    public void select(@PathVariable String id) {
//        log.info("Selecting: " + id);
        if (!id.equals("undefined")) {
            bookService.select(id);
        }
    }

    @GetMapping(value = "/books/{id}/cover")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        if (!id.equals("undefined")) {
            Book book = bookService.findById(id);
            String cover = book.getCover();
            byte[] image = Base64.getDecoder().decode(cover);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        }
        log.info("Getting cover for: " + id);
        return null;
    }
}