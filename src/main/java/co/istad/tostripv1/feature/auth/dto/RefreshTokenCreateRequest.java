package co.istad.tostripv1.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCreateRequest(
        @NotBlank(message = "Refresh token is require!!!")
        String refreshToken
) {
}
