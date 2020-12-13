package sanguino.board.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User has comments")
public class UserHasCommentsException extends RuntimeException {

}
