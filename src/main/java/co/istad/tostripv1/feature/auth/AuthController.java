package co.istad.tostripv1.feature.auth;

import co.istad.tostripv1.feature.auth.dto.*;
import co.istad.tostripv1.feature.user.UserService;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    // get all roles
    @GetMapping("/me")
    public UserResponse getMe(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        // Extract username from "jti"
        String username = jwt.getClaim("jti"); // Use "jti" instead of "sub"

        if (username == null) {
            throw new RuntimeException("Username not found in token claims");
        }

        System.out.println("Extracted username: " + username); // Debugging log
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/{username}/roles/{roleName}")
    public ResponseEntity<?> removeRole(@PathVariable String username, @PathVariable String roleName) {
        authService.removeRoleFromUser(username, roleName);
        return ResponseEntity.ok("Role " + roleName + " removed from user : " + username);
    }
    // for debugging purpose
    @GetMapping
    public Map<String, Object> debugAuth(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Map.of(
                "username", authentication.getName(),
                "authorities", authorities
        );
    }

    @PatchMapping("/{username}/add-roles")
    public ResponseEntity<UserResponse> addRolesToUser(
            @PathVariable String username,
            @RequestBody AddRoleCreateRequest addRoleCreateRequest) {
        return ResponseEntity.ok(authService.addRoleToUser(username, addRoleCreateRequest));
    }

    // register a new user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    UserResponse register(@Valid @RequestBody UserCreateRequest registerRequest) {
        return authService.register(registerRequest);
    }


    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh-token")
    AuthResponse refreshToken(@Valid @RequestBody RefreshTokenCreateRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
}
