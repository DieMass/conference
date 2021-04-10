package die.mass.conference.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationFilter() {
		super("/**");
	}

//	@Value("${jwt.secret}")
	private String secret = "qwerty";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws AuthenticationException {
		String token = null;
		try {
			token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (token != null) {
				Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
				Authentication authentication = new JwtAuthentication(token);
				return getAuthenticationManager().authenticate(authentication);
			} else {
				return SecurityContextHolder.getContext().getAuthentication();
			}
		} catch (ExpiredJwtException ex) {
			httpServletResponse.setStatus(401);
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("Some exception");
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
											final FilterChain chain, final Authentication authResult)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		this.getRememberMeServices().loginSuccess(request, response, authResult);
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}
		chain.doFilter(request, response);
	}
}