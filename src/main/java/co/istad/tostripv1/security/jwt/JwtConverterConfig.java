package co.istad.tostripv1.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverterConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract roles from the 'scope' field
            String scope = jwt.getClaimAsString("scope");
            if (scope == null || scope.isEmpty()) {
                return List.of();
            }

            // Convert space-separated roles to a list of GrantedAuthority
            Collection<String> roles = Arrays.asList(scope.split(" "));
            return roles.stream()
                    .map(SimpleGrantedAuthority::new) // Convert to Spring Security authorities
                    .collect(Collectors.toList());
        });

        return converter;
    }
}