package org.lslonina.books.safaricrawler.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.lslonina.books.safaricrawler.dto.Book;
import org.lslonina.books.safaricrawler.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll(Sort.by("added").descending());
    }

    public Page<Book> findAll(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public Page<Book> findSkipped(PageRequest pageRequest) {
        return bookRepository.findAllByPriorityLessThanAndLanguageEquals(0, "en", pageRequest);
    }

    public Page<Book> findSelected(PageRequest pageRequest) {
        return bookRepository.findAllByPriorityEqualsAndLanguageEquals(1, "en", pageRequest);
    }

    public Page<Book> findAllSelected() {
        return bookRepository.findAllByPriorityGreaterThanAndLanguageEquals(0, "en", PageRequest.of(0, Integer.MAX_VALUE, Sort.by("modificationTimestamp").descending()));
    }

    public Page<Book> findUnprocessed(PageRequest pageRequest) {
        return bookRepository.findAllByPriorityEqualsAndLanguageEquals(0, "en", pageRequest);
    }

    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void skip(String id) {
        updateBookPriority(id, -1);
    }

    public void select(String id) {
        Book byId = findById(id);
        updateBookPriority(id, byId.getPriority() + 1);
    }

    private void updateBookPriority(String id, int priority) {
        Book book = findById(id);
        book.setModificationTimestamp(new Date());
        book.setPriority(priority);
        bookRepository.save(book);
    }

    public void export() {
        List<Book> all = bookRepository.findAll();
        List<Book> selected = all.stream().filter(b -> b.getPriority() > 0).collect(Collectors.toList());
        List<Book> ignored = all.stream().filter(b -> b.getPriority() < 0).collect(Collectors.toList());

        export(selected, "selected");
        export(ignored, "ignored");
        System.out.println("Exported.");
    }

    public void export(List<Book> books, String prefix) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm");
        String strDate = dateFormat.format(date);
        String id = books.stream().map(book -> book.getIdentifier() + "," + book.getPriority()).collect(Collectors.joining("\n"));
        try {
            Files.write(Path.of("D:/data/books/" + prefix + "-id-" + strDate + ".csv"), id.trim().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Wasn't able to store file: " + prefix, e);
        }

    }

}
