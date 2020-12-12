package sanguino.board.repositories;

import org.springframework.stereotype.Repository;
import sanguino.board.model.Comment;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CommentMemoryRepository {

    private ConcurrentMap<Long, Comment> comments = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public CommentMemoryRepository() {
        this.addComment(new Comment(0L, "Peter", "Great Book", 42));
    }

    public Comment addComment(Comment comment) {
        long id = this.nextId.getAndIncrement();
        comment.setId(id);
        this.comments.put(id, comment);
        return comment;
    }

    public List<Comment> findByBookId(long id) {
        return comments.values().stream()
                .filter(comment -> comment.getBookId() == id)
                .collect(Collectors.toList());
    }

    public Optional<Comment> findByBookIdAndId(long bookId, long id) {
        Comment comment = comments.get(id);
        if (comment != null && comment.getBookId() == bookId) {
            return Optional.ofNullable(comment);
        }
        return Optional.empty();
    }

    public Comment deleteCommentById(long id) {
        return this.comments.remove(id);
    }
}
