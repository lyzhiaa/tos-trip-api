package co.istad.tostripv1.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        //Token type
        String tokenType,
        String accessToken
//        String refreshToken
) {
}
