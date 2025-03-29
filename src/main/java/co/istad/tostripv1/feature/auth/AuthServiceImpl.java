package co.istad.tostripv1.feature.auth;

import co.istad.tostripv1.domain.Role;
import co.istad.tostripv1.domain.User;
import co.istad.tostripv1.feature.auth.dto.*;
import co.istad.tostripv1.feature.role.RoleRepository;
import co.istad.tostripv1.feature.user.UserRepository;
import co.istad.tostripv1.feature.user.dto.UserCreateRequest;
import co.istad.tostripv1.feature.user.dto.UserResponse;
import co.istad.tostripv1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;

    @Override
    public UserResponse register(UserCreateRequest registerRequest) {
        //validate' s username
        if (userRepository.existsByUsername(registerRequest.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This username number is already used!!");
        }
        User user  = userMapper.fromUserCreateRequest(registerRequest);

        //set system data
        user.setUuid(UUID.randomUUID().toString());
        user.setCreatedAt(String.valueOf(LocalTime.now()));
        user.setUpdatedAt(String.valueOf(LocalTime.now()));
        user.setDisabled(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "User Role not set."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication auth = new UsernamePasswordAuthenticationToken
                (loginRequest.username(), loginRequest.password());
        auth = daoAuthenticationProvider.authenticate(auth);

        //ROLE_USER ROLE_ADMIN //separate by white space
        String scope = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("Scope: {}",scope);
        log.info("Auth: {}", auth.getPrincipal());

        // Generate JWT token by JwtEncoder
        // 1. Define JwtClaimsSet (Payload)
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access APIs")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.SECONDS))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("scope", scope)
                .build();

        //---------------------------------------------------------
        //---------------| Refresh Token |-------------------------
        //---------------------------------------------------------

        JwtClaimsSet jwtRefreshTokenClimeSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Refresh Token")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("scope", scope)
                .build();

        // 2. Generate Token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
        log.info("Access Token : {}", accessToken);
        //---------------| Refresh Token |-------------------------
        String refreshToken = refreshTokenJwtEncoder
                .encode(JwtEncoderParameters
                        .from(jwtRefreshTokenClimeSet)).getTokenValue();
//        ----------------------------------------------------------

        // get token
        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenCreateRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        // Authenticate client with refresh token
        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        auth = jwtAuthenticationProvider.authenticate(auth);
        log.info("Auth : {}", auth.getPrincipal());

        Jwt jwt = (Jwt) auth.getPrincipal();

        // 1. Define JwtClaimsSet (Payload)
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access APIs")
                .issuer(jwt.getId())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.DAYS))
//                .audience(jwt.getAudience())
//                .claim("isAdmin", true)
//                .claim("studentId", "ISTAD0010")
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        // 2. Generate Token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
        log.info("Access Token : {}", accessToken);

        //Get expiration of refresh token
        Instant expiresAt = jwt.getExpiresAt();
        long remainingDays = Duration.between(now, expiresAt).toDays();
        log.info("remainingDays: {}", remainingDays);
        if (remainingDays <= 1 ) {
            JwtClaimsSet jwtRefreshTokenClimeSet = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .subject("Refresh Token")
                    .issuer(auth.getName())
                    .issuedAt(now)
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
//                    .audience(List.of("NextJS", "Android", "iOS"))
//                    .claim("isAdmin", true)
//                    .claim("studentId", "ISTAD0010")
                    .claim("scope", jwt.getClaimAsString("scope"))
                    .build();
            refreshToken = refreshTokenJwtEncoder
                    .encode(JwtEncoderParameters
                            .from(jwtRefreshTokenClimeSet)).getTokenValue();
        }
        return AuthResponse
                .builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .refreshToken(refreshTokenRequest.refreshToken())
                .build();
    }

}


