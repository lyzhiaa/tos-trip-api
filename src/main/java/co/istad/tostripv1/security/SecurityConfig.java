package co.istad.tostripv1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;


    @Bean
    JwtAuthenticationProvider configJwtAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder")
                                                              JwtDecoder refreshTokenJwtDecoder) {
        return new JwtAuthenticationProvider(refreshTokenJwtDecoder);
    }
    @Bean
    DaoAuthenticationProvider configDaoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);

        return auth;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173",
                "http://127.0.0.1:5500",
                "http://127.0.0.1:5501",
                "http://localhost:5500",
                "http://localhost:5501",
                "http://202.178.125.77:8169",
                "https://derlg.vercel.app/",
                "https://derleng.vercel.app/",
                "derleng.eunglyzhia.social",
                "https://deploy-final-project-q3sq.vercel.app/",
                "https://tostrip.eunglyzhia.social"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity httpSecurity,
                                             @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder, AbstractUserDetailsAuthenticationProvider abstractUserDetailsAuthenticationProvider) throws Exception {
        return httpSecurity
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource()) // This line enables your CORS
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->authorizeRequests.anyRequest().permitAll())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/images/**").permitAll()
//                        .requestMatchers("/api/v1/upload/**").permitAll()
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/users/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers("/api/v1/reviews/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/places/**").permitAll()
//                        .requestMatchers("/api/v1/places/**").permitAll()
//                        .requestMatchers("/api/v1/categories/**").permitAll()
//                        .requestMatchers("/api/v1/roles/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .anyRequest().authenticated()
//                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .build();
    }


}
