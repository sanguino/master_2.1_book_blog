package sanguino.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sanguino.board.dtos.request.UserCreateRequestDto;
import sanguino.board.dtos.request.UserPatchRequestDto;
import sanguino.board.dtos.response.CommentForUserResponseDto;
import sanguino.board.dtos.response.UserResponseDto;
import sanguino.board.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @PostMapping("/users")
    public UserResponseDto newUser(@RequestBody UserCreateRequestDto user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @Override
    @GetMapping("/users/{nick}")
    public UserResponseDto showUser(@PathVariable String nick) {
        return userService.findById(nick);
    }

    @Override
    @GetMapping("/users/{nick}/comments")
    public Collection<CommentForUserResponseDto> showUserComments(@PathVariable String nick) {
        return userService.findCommentsById(nick);
    }

    @Override
    @PatchMapping("/users/{nick}")
    public UserResponseDto modifyUser(@PathVariable String nick, @RequestBody UserPatchRequestDto user) {
        return userService.updateBy(nick, user);
    }

    @Override
    @DeleteMapping("/users/{nick}")
    public UserResponseDto deleteUser(@PathVariable String nick) {
        return this.userService.deleteById(nick);
    }
}