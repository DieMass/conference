package die.mass.conference.services.interfaces;

import die.mass.conference.models.User;
import io.jsonwebtoken.Claims;

public interface TokenService {

	String generateAccessToken(User customer);

}
