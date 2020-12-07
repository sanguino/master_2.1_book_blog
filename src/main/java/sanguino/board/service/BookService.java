package sanguino.board.service;

import sanguino.board.model.Book;
import org.springframework.stereotype.Service;
import sanguino.board.model.Comment;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public BookService () {
        this.save(new Book("Guia del autoestopista galactico", "un buen libro", "Douglas Adams", "Anagrama", 1979 ));
    }

    public Collection<Book> findAll() {
        return this.books.values();
    }

    public void save(Book book) {

        long id = this.nextId.getAndIncrement();

        book.setId(id);

        this.books.put(id, book);
    }

    public Book findById(long id) {
        return this.books.get(id);
    }
}
