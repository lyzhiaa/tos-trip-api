package co.istad.tostripv1.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username is require!!")
        String username,
        @NotBlank(message = "Password is require !!!")
        String password
) {
}
