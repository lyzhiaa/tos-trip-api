package co.istad.tostripv1.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        String username,
        String firstname,
        String lastname,
        String gender,
        String email,
        String password
) {
}
