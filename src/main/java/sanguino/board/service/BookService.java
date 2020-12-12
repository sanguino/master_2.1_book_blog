package sanguino.board.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sanguino.board.dtos.request.BookRequestDto;
import sanguino.board.dtos.request.CommentRequestDto;
import sanguino.board.dtos.response.BookBasicResponseDto;
import sanguino.board.dtos.response.BookCompleteResponseDto;
import sanguino.board.dtos.response.CommentResponseDto;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;
import sanguino.board.model.User;
import sanguino.board.repositories.BookRepository;
import sanguino.board.repositories.CommentRepository;
import sanguino.board.repositories.UserRepository;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.modelMapper = new ModelMapper();
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
        User user = this.userRepository.findById(commentRequestDto.getNick()).orElseThrow();
        Comment comment = this.modelMapper.map(commentRequestDto, Comment.class);
        comment.setBook(book);
        comment.setUser(user);
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
