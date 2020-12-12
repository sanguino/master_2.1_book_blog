package sanguino.board.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sanguino.board.dtos.*;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.repositories.BookMemoryRepository;
import sanguino.board.repositories.CommentMemoryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookMemoryRepository bookRepository;
    private CommentMemoryRepository commentRepository;
    private ModelMapper modelMapper;

    public BookService(BookMemoryRepository bookRepository, CommentMemoryRepository commentRepository) {
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
        ;
        book.setComments(this.commentRepository.findByBookId(id));
        return this.modelMapper.map(book, BookCompleteResponseDto.class);
    }

    public CommentResponseDto addComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = this.modelMapper.map(commentRequestDto, Comment.class);
        comment.setBookId(id);
        comment = this.commentRepository.addComment(comment);
        return this.modelMapper.map(comment, CommentResponseDto.class);
    }

    public CommentResponseDto deleteCommentById(long bookId, long commentId) {
        this.commentRepository.findByBookIdAndId(bookId, commentId).orElseThrow();
        Comment comment = this.commentRepository.deleteCommentById(commentId);
        return this.modelMapper.map(comment, CommentResponseDto.class);
    }
}
