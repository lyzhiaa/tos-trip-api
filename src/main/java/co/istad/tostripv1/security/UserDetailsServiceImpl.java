package co.istad.tostripv1.security;

import co.istad.tostripv1.domain.User;
import co.istad.tostripv1.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    // get me

    public UserDetails loadUserByJwt(Jwt jwt) {
        String username = jwt.getClaim("sub"); // "sub" usually contains the username in JWT
        return loadUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("This user is not been found!!!"));


        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);
        return customUserDetails;
    }
}
