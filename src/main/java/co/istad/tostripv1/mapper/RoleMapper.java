package co.istad.tostripv1.mapper;

import co.istad.tostripv1.domain.Role;
import co.istad.tostripv1.feature.role.dto.RoleCreateRequest;
import co.istad.tostripv1.feature.role.dto.RoleResponse;
import co.istad.tostripv1.feature.role.dto.RoleUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // create role
    Role fromRoleCreateRequest(RoleCreateRequest roleCreateRequest);
    // get role by id
    RoleResponse toRoleResponse(Role role);
    // get all roles
    List<RoleResponse> toRoleResponseList(List<Role> roles);
    // update role
    void fromRoleUpdateRequest(RoleUpdateRequest roleUpdateRequest, @MappingTarget Role role);

}
