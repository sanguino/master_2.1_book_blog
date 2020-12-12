package sanguino.board.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sanguino.board.model.Comment;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {

    private ConcurrentMap<Long, Comment> comments = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public CommentRepository() {
        this.addComment(new Comment(0L, "Peter", "Great Book", 42));
    }

    public void addComment(Comment comment) {
        long id = this.nextId.getAndIncrement();
        comment.setId(id);
        this.comments.put(id, comment);
    }

    public List<Comment> findByBookId(long id) {
        return comments.values().stream()
                .filter(comment -> comment.getBookId() == id)
                .collect(Collectors.toList());
    }

    public Comment findById(long id) {
        return comments.get(id);
    }

    public void deleteCommentById(long commentId) {
        this.comments.remove(commentId);
    }
}
