package sanguino.board.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanguino.board.model.Book;
import sanguino.board.model.BookWithComments;
import sanguino.board.model.Comment;
import sanguino.board.model.UserSession;
import sanguino.board.service.BookService;
import sanguino.board.service.CommentService;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/rest")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserSession userSession;

	@JsonView(Book.Basic.class)
	@GetMapping("/book")
	public Collection<Book> listBooks() {
		return this.bookService.findAll();
	}

	@PostMapping("/book")
	public ResponseEntity<Book> newBook(@RequestBody Book book) {
		bookService.save(book);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
		return ResponseEntity.created(location).body(book);
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Book> showPost(@PathVariable long id) {
		Book book = bookService.findById(id);
		if (book != null) {
			BookWithComments bookWithComments = new BookWithComments(book, commentService.findByBookId(id));
			return ResponseEntity.ok(bookWithComments);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/book/{bookId}/comment")
	public ResponseEntity<Comment> newComment(@RequestBody Comment comment, @PathVariable long bookId ) {

		comment.setBookId(bookId);
		commentService.addComment(comment);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
		return ResponseEntity.created(location).body(comment);
	}

	@DeleteMapping("/book/{bookId}/comment/{commentId}")
	public ResponseEntity<Comment> deleteComment(@PathVariable long bookId, @PathVariable long commentId ) {

		Comment comment = commentService.findById(commentId);

		if (comment != null && comment.getBookId() == bookId) {
			commentService.deleteCommentById(commentId);
			return ResponseEntity.ok(comment);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}