package co.istad.tostripv1.feature.user.dto;

public record UserCreateRequest(
        String username,
        String firstname,
        String lastname,
        String gender,
        String email,
        String password
) {
}
