package sanguino.board.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sanguino.board.dtos.request.UserCreateRequestDto;
import sanguino.board.dtos.request.UserPatchRequestDto;
import sanguino.board.dtos.response.UserResponseDto;
import sanguino.board.exceptions.ConflictException;
import sanguino.board.model.Comment;
import sanguino.board.model.User;
import sanguino.board.repositories.CommentRepository;
import sanguino.board.repositories.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, CommentRepository commentRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public UserResponseDto save(UserCreateRequestDto userCreateRequestDto) {
        if (this.userRepository.existsById(userCreateRequestDto.getNick())) {
            throw new ConflictException();
        }
        User user = this.modelMapper.map(userCreateRequestDto, User.class);
        user = this.userRepository.save(user);
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto findById(String nick) {
        User user = this.userRepository.findById(nick).orElseThrow();
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto updateBy(String nick, UserPatchRequestDto userPatchRequestDto) {
        User user = this.userRepository.findById(nick).orElseThrow();
        user.setEmail(userPatchRequestDto.getEmail());
        user = this.userRepository.save(user);
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto deleteById(String nick) {
        User user = this.userRepository.findById(nick).orElseThrow();
        this.userRepository.deleteById(nick);
        return this.modelMapper.map(user, UserResponseDto.class);
    }
}
