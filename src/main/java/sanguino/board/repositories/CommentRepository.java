package sanguino.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sanguino.board.model.Comment;
import sanguino.board.model.User;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findByUser(User user);
}