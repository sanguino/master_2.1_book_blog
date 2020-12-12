package sanguino.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.model.UserSession;
import sanguino.board.repositories.CommentRepository;
import sanguino.board.service.BookService;

import java.util.List;

@Controller
public class BookWebController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserSession userSession;

    @GetMapping("/")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "index";
    }

    @GetMapping("/book")
    public String newBookForm() {
        return "new_book";
    }

    @PostMapping("/book")
    public String newBook(Model model, Book book) {
        bookService.save(book);
        return "saved_book";
    }

    @GetMapping("/book/{id}")
    public String showPost(Model model, @PathVariable long id) {
        Book book = bookService.findById(id);

        model.addAttribute("user", userSession.getUser());
        model.addAttribute("book", book);
        model.addAttribute("comments", book.getComments());
        return "show_book";
    }

    @PostMapping("/book/{bookId}/comment")
    public String newComment(Model model, Comment comment, @PathVariable long bookId) {
        comment.setBookId(bookId);
        userSession.setUser(comment.getName());
        bookService.addComment(comment);

        model.addAttribute("id", bookId);
        model.addAttribute("type", "saved");
        return "saved_comment";
    }

    @GetMapping("/book/{bookId}/comment/{commentId}/delete")
    public String deleteComment(Model model, Comment comment, @PathVariable long bookId, @PathVariable long commentId) {
        bookService.deleteCommentById(bookId, commentId);

        model.addAttribute("id", bookId);
        model.addAttribute("type", "deleted");
        return "saved_comment";
    }
}