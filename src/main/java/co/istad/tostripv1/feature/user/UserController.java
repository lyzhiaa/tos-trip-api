package co.istad.tostripv1.feature.user;

import co.istad.tostripv1.domain.AuthRequest;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import co.istad.tostripv1.feature.user.dto.UserUpdateRequest;
import co.istad.tostripv1.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return jwtService.generateToken(request.getUsername()); // Generate a JWT token
    }

    // create user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserResponse createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    // get all users
    @GetMapping
    List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    // get user by uuid
    @GetMapping("/{uuid}")
    UserResponse getUserByUuid(@Valid @PathVariable("uuid") String uuid) {
        return userService.getUserByUuid(uuid);
    }

    // get user by username
    @GetMapping("/username/{username}")
    UserResponse getUserByUsername(@Valid @PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    // update user
    @PatchMapping("/{uuid}")
    UserResponse updateUser(@PathVariable("uuid") String uuid, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(uuid, userUpdateRequest);
    }

    // disable user by uuid
    @PatchMapping("/{uuid}/disable")
    void disableUser(@PathVariable("uuid") String uuid) {
        userService.disableUser(uuid);
    }

    // disable user by uuid
    @PatchMapping("/{uuid}/enable")
    void enableUser(@PathVariable("uuid") String uuid) {
        userService.enableUser(uuid);
    }

    // delete user by uuid
    @DeleteMapping("/{uuid}")
    void deleteUser(@PathVariable("uuid") String uuid) {
        userService.deleteUser(uuid);
    }
}