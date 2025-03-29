package co.istad.tostripv1.feature.user.dto;

public record UserResponse(
        Integer id,
        String uuid,
        String username,
        String firstname,
        String lastname,
        String gender,
        String email,
        String password,
        String createdAt,
        String updatedAt,
        Boolean disabled

) {
}
