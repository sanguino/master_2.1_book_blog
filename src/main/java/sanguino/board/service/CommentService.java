package sanguino.board.service;

import org.springframework.stereotype.Service;
import sanguino.board.model.Book;
import sanguino.board.model.Comment;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private ConcurrentMap<Long, Comment> comments = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public void addComment(Comment comment) {

        long id = this.nextId.getAndIncrement();
        comment.setId(id);
        this.comments.put(id, comment);
    }

    public List<Comment> findById(long id) {
        return comments.values().stream()
                .filter(comment -> comment.getBookId() == id)
                .collect(Collectors.toList());
    }
}
