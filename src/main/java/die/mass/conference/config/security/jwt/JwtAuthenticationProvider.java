package die.mass.conference.config.security.jwt;

import die.mass.conference.config.security.UserDetailsImpl;
import die.mass.conference.models.User;
import die.mass.conference.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final UserRepository userRepository;

	@Value("${jwt.secret}")
	private String secret;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = authentication.getName();
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("Bad token");
		}
		Long id = Long.parseLong(claims.get("sub", String.class));
		Optional<User> userCandidate = userRepository.findById(id);
		if (userCandidate.isPresent()) {
			UserDetailsImpl userDetails = UserDetailsImpl.builder()
					.user(userCandidate.get())
					.userId(id)
					.role(claims.get("role", String.class))
					.username(claims.get("name", String.class))
					.build();
			authentication.setAuthenticated(true);
			((JwtAuthentication) authentication).setUserDetails(userDetails);
		}
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthentication.class.isAssignableFrom(authentication);
	}
}