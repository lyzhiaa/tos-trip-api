package co.istad.tostripv1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

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
    SecurityFilterChain configureApiSecurity(HttpSecurity httpSecurity,
                                             @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder) throws Exception {
        //endpoint security config
        httpSecurity.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/api/v1/upload/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyAuthority("admin", "ADMIN")
                .requestMatchers("/api/v1/user/**").hasAnyAuthority("admin", "ADMIN")
                .requestMatchers("/api/v1/reviews/**").permitAll()
                .requestMatchers("/api/v1/roles/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/places/**").permitAll()
                .requestMatchers("/api/v1/places/**").hasAnyAuthority("admin", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .requestMatchers("/api/v1/categories/**").hasAnyAuthority("admin", "ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
//                .anyRequest().permitAll());
                .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(jwt -> jwt.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)));

        //disable CSRF (Cross site request forgery) token
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //Make Stateless Session
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
