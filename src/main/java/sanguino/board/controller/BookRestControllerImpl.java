package sanguino.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sanguino.board.dtos.*;
import sanguino.board.service.BookService;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class BookRestControllerImpl implements BookRestController {

    @Autowired
    private BookService bookService;

    @Override
    @GetMapping("/books")
    public Collection<BookBasicResponseDto> listBooks() {
        return this.bookService.findAll();
    }

    @Override
    @PostMapping("/books")
    public BookCompleteResponseDto newBook(@RequestBody BookRequestDto book) {
        return bookService.save(book);
    }

    @Override
    @GetMapping("/books/{id}")
    public BookCompleteResponseDto showPost(@PathVariable long id) {
        return bookService.findById(id);
    }

    @Override
    @PostMapping("/books/{id}/comments")
    public CommentResponseDto newComment(@RequestBody CommentRequestDto comment, @PathVariable long id) {
        return bookService.addComment(id, comment);
    }

    @Override
    @DeleteMapping("/books/{bookId}/comments/{commentId}")
    public CommentResponseDto deleteComment(@PathVariable long bookId, @PathVariable long commentId) {
        return this.bookService.deleteCommentById(bookId, commentId);
    }
}