package sanguino.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.model.UserSession;
import sanguino.board.service.BookService;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/rest")
public class BookRestControllerImpl implements BookRestController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserSession userSession;

    @Override
    @GetMapping("/books")
    public Collection<Book> listBooks() {
        return this.bookService.findAll();
    }

    @Override
    @PostMapping("/books")
    public ResponseEntity<Book> newBook(@RequestBody Book book) {
        bookService.save(book);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(location).body(book);
    }

    @Override
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> showPost(@PathVariable long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PostMapping("/books/{id}/comments")
    public ResponseEntity<Comment> newComment(@RequestBody Comment comment, @PathVariable long id) {
        comment.setBookId(id);
        bookService.addComment(comment);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @Override
    @DeleteMapping("/books/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long bookId, @PathVariable long commentId) {
        return this.bookService.deleteCommentById(bookId, commentId);
    }
}