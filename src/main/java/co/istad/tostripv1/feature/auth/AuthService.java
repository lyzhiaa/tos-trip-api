package co.istad.tostripv1.feature.auth;


import co.istad.tostripv1.feature.auth.dto.*;
import co.istad.tostripv1.feature.role.dto.RoleUpdateRequest;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;

import java.util.Set;

public interface AuthService {
    UserResponse register(UserCreateRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenCreateRequest refreshTokenRequest);
    UserResponse addRoleToUser(String username, AddRoleCreateRequest addRoleCreateRequest);
    void removeRoleFromUser(String username, String roleName);
}
