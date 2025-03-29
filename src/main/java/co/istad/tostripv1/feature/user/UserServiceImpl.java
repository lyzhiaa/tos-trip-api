package co.istad.tostripv1.feature.user;

import co.istad.tostripv1.domain.User;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import co.istad.tostripv1.feature.user.dto.UserUpdateRequest;
import co.istad.tostripv1.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import co.istad.tostripv1.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    // create user
    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        // validate if user already exists by username or email
        if (userRepository.existsByUsernameOrEmail(userCreateRequest.username(), userCreateRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already exists");
        }

        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setCreatedAt(String.valueOf(LocalDateTime.now()));
        user.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        user.setDisabled(false);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    // get all users
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toUserResponseList(users);
    }

    // get user by uuid
    @Override
    public UserResponse getUserByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userMapper.toUserResponse(user);
    }

    // get user by username
    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userMapper.toUserResponse(user);
    }

    // update user by uuid
    @Override
    public UserResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userMapper.fromUserUpdateRequest(userUpdateRequest, user);
        user.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        // save into database
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    // disable user by uuid
    @Override
    public void disableUser(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setDisabled(true);
        userRepository.save(user);
    }

    // enable user by uuid
    @Override
    public void enableUser(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setDisabled(false);
        userRepository.save(user);
    }

    // delete user
    @Override
    public void deleteUser(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
    }
}
