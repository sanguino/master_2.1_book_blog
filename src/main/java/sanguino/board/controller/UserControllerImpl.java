package sanguino.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sanguino.board.dtos.request.UserCreateRequestDto;
import sanguino.board.dtos.request.UserPatchRequestDto;
import sanguino.board.dtos.response.CommentResponseDto;
import sanguino.board.dtos.response.UserResponseDto;
import sanguino.board.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class UserControllerImpl {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserResponseDto newUser(@RequestBody UserCreateRequestDto user) {
        return userService.save(user);
    }

    @GetMapping("/users/{nick}")
    public UserResponseDto showUser(@PathVariable String nick) {
        return userService.findById(nick);
    }

    @GetMapping("/users/{nick}/comments")
    public Collection<CommentResponseDto> showUserComments(@PathVariable String nick) {
        return userService.findCommentsById(nick);
    }

    @PatchMapping("/users/{nick}")
    public UserResponseDto showPost(@PathVariable String nick, @RequestBody UserPatchRequestDto user) {
        return userService.updateBy(nick, user);
    }

    @DeleteMapping("/users/{nick}")
    public UserResponseDto deleteComment(@PathVariable String nick) {
        return this.userService.deleteById(nick);
    }
}