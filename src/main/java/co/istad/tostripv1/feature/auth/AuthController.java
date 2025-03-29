package co.istad.tostripv1.feature.auth;

import co.istad.tostripv1.feature.auth.dto.*;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;



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
