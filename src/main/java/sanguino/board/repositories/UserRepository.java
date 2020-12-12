package sanguino.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sanguino.board.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}