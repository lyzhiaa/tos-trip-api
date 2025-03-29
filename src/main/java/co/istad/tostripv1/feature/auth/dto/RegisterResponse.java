package co.istad.tostripv1.feature.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String username,
        String password
) {
}
