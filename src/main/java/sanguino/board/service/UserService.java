package sanguino.board.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sanguino.board.dtos.request.UserCreateRequestDto;
import sanguino.board.dtos.request.UserPatchRequestDto;
import sanguino.board.dtos.response.CommentForUserResponseDto;
import sanguino.board.dtos.response.UserResponseDto;
import sanguino.board.exceptions.UserExistsException;
import sanguino.board.exceptions.UserHasCommentsException;
import sanguino.board.model.User;
import sanguino.board.repositories.CommentRepository;
import sanguino.board.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, CommentRepository commentRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findById(nick);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(nick);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getNick(), user.get().getPassword(), emptyList());
    }

    public UserResponseDto save(UserCreateRequestDto userCreateRequestDto) {
        if (this.userRepository.existsById(userCreateRequestDto.getNick())) {
            throw new UserExistsException();
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
        if (this.commentRepository.countByUser(user) != 0) {
            throw new UserHasCommentsException();
        }
        this.userRepository.deleteById(nick);
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    public Collection<CommentForUserResponseDto> findCommentsById(String nick) {
        User user = this.userRepository.findById(nick).orElseThrow();
        return this.commentRepository.findByUser(user).stream()
                .map(comment -> this.modelMapper.map(comment, CommentForUserResponseDto.class))
                .collect(Collectors.toList());
    }
}
