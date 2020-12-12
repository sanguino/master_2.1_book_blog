package sanguino.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sanguino.board.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}