package sanguino.board.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sanguino.board.dtos.*;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.repositories.BookRepository;
import sanguino.board.repositories.CommentRepository;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, CommentRepository commentRepository) {
        this.modelMapper = new ModelMapper();
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    public Collection<BookBasicResponseDto> findAll() {
        return this.bookRepository.findAll().stream()
                .map(book -> this.modelMapper.map(book, BookBasicResponseDto.class))
                .collect(Collectors.toList());
    }

    public BookCompleteResponseDto save(BookRequestDto bookRequestDto) {
        Book book = this.modelMapper.map(bookRequestDto, Book.class);
        book = this.bookRepository.save(book);
        return this.modelMapper.map(book, BookCompleteResponseDto.class);
    }

    public BookCompleteResponseDto findById(long id) {
        Book book = this.bookRepository.findById(id).orElseThrow();
        return this.modelMapper.map(book, BookCompleteResponseDto.class);
    }

    public CommentResponseDto addComment(Long id, CommentRequestDto commentRequestDto) {
        Book book = this.bookRepository.findById(id).orElseThrow();
        Comment comment = this.modelMapper.map(commentRequestDto, Comment.class);
        comment.setBook(book);
        this.commentRepository.save(comment);
        return this.modelMapper.map(comment, CommentResponseDto.class);
    }

    public CommentResponseDto deleteCommentById(long bookId, long commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow();
        if (comment.getBook().getId() != bookId) {
            throw new NoSuchElementException();
        }
        this.commentRepository.deleteById(commentId);
        return this.modelMapper.map(comment, CommentResponseDto.class);
    }
}
