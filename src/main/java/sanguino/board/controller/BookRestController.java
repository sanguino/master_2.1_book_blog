package sanguino.board.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get a list of books (id and title only)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Books found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Book.Basic.class)
                                    )
                            )
                    }
            )
    })
    @JsonView(Book.Basic.class)
    @GetMapping("/books")
    public Collection<Book> listBooks() {
        return this.bookService.findAll();
    }

    @Operation(summary = "Create a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Book.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
    })
    @PostMapping("/books")
    public ResponseEntity<Book> newBook(@RequestBody Book book) {
        bookService.save(book);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(location).body(book);
    }

    @Operation(summary = "Show book info and comments by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookWithComments.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> showPost(@PathVariable long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            BookWithComments bookWithComments = new BookWithComments(book, commentService.findByBookId(id));
            return ResponseEntity.ok(bookWithComments);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a comment by book id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
    })
    @PostMapping("/books/{bookId}/comments")
    public ResponseEntity<Comment> newComment(@RequestBody Comment comment, @PathVariable long bookId) {
        comment.setBookId(bookId);
        commentService.addComment(comment);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @Operation(summary = "Delete a comment by book id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
                            )
                    }

            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found",
                    content = @Content
            )
    })
    @DeleteMapping("/books/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long bookId, @PathVariable long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment != null && comment.getBookId() == bookId) {
            commentService.deleteCommentById(commentId);
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }
}