package co.istad.tostripv1.feature.user;


import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import co.istad.tostripv1.feature.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    // create user
    UserResponse createUser(UserCreateRequest userCreateRequest);
    // get all users
    List<UserResponse> getAllUsers();
    // get user by uuid
    UserResponse getUserByUuid(String uuid);
    // get user by username
    UserResponse getUserByUsername(String username);
    // update user
    UserResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest);
    // disable user by username
    void disableUser(String uuid);
    // enable user by username
    void  enableUser(String uuid);
    // delete user profile
    void deleteUser(String uuid);
}
