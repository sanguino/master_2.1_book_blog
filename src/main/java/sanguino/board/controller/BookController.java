package sanguino.board.controller;

import org.springframework.web.bind.annotation.PathVariable;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sanguino.board.service.CommentService;

import java.util.List;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CommentService commentService;

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
		List<Comment> comments = commentService.findById(id);

		model.addAttribute("book", book);
		model.addAttribute("comments", comments);

		return "show_book";
	}

	@PostMapping("/book/{bookId}/comment")
	public String newComment(Model model, Comment comment, @PathVariable long bookId ) {
		comment.setBookId(bookId);
		commentService.addComment(comment);

		model.addAttribute("id", bookId);
		return "saved_comment";
	}
}