package co.istad.tostripv1.feature.role;

import co.istad.tostripv1.feature.role.dto.RoleCreateRequest;
import co.istad.tostripv1.feature.role.dto.RoleResponse;
import co.istad.tostripv1.feature.role.dto.RoleUpdateRequest;

import java.util.List;

public interface RoleService {
    // create role
    RoleResponse createRole(RoleCreateRequest roleCreateRequest);
    // get all roles
    List<RoleResponse> getAllRoles();
    // get role by id
    RoleResponse getRoleByUuid(String uuid);
    // update role
    RoleResponse updateRoleByUuid(String uuid, RoleUpdateRequest roleUpdateRequest);
    // delete role
    void deleteRoleByUuid(String uuid);
}
