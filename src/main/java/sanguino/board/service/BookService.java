package sanguino.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.repositories.BookRepository;
import sanguino.board.repositories.CommentRepository;

import java.util.Collection;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Collection<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public void save(Book book) {
        this.bookRepository.save(book);
    }

    public Book findById(long id) {
        Book book = this.bookRepository.findById(id);
        book.setComments(this.commentRepository.findByBookId(id));
        return book;
    }

    public void addComment(Comment comment) {
        this.commentRepository.addComment(comment);
    }

    public ResponseEntity<Comment> deleteCommentById(long bookId, long commentId) {
        Comment comment = this.commentRepository.findById(commentId);
        if (comment != null && comment.getBookId() == bookId) {
            this.commentRepository.deleteCommentById(commentId);
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }
}
