package sanguino.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sanguino.board.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}