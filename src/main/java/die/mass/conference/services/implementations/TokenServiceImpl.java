package die.mass.conference.services.implementations;

import die.mass.conference.models.User;
import die.mass.conference.services.interfaces.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	@Value("${jwt.secret}")
	private String secret;

	@Override
	public String generateAccessToken(User customer) {
		return Jwts.builder()
				.setSubject(customer.getId().toString())
				.claim("name", customer.getEmail())
				.claim("role", customer.getRole().name())
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
}