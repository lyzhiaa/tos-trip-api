package co.istad.tostripv1.feature.auth.dto;

import java.util.Set;

public record AddRoleCreateRequest (
        Set<String> roles // ✅ Must be a Set or List, not a single String
) {
}
