package co.istad.tostripv1.feature.auth;


import co.istad.tostripv1.feature.auth.dto.*;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;

public interface AuthService {
    UserResponse register(UserCreateRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenCreateRequest refreshTokenRequest);
}
