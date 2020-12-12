package sanguino.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sanguino.board.dtos.request.UserCreateRequestDto;
import sanguino.board.dtos.request.UserPatchRequestDto;
import sanguino.board.dtos.response.UserResponseDto;
import sanguino.board.service.UserService;

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
    public UserResponseDto showUserComments(@PathVariable String nick) {
        return userService.findById(nick);
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