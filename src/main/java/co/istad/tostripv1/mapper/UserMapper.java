package co.istad.tostripv1.mapper;

import co.istad.tostripv1.domain.User;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import co.istad.tostripv1.feature.user.dto.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // create user request to user entity
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    // search user by name
    UserResponse toUserResponse(User user);

    // all users to user response
    List<UserResponse> toUserResponseList(List<User> users);

    // update user request to user entity
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
}
