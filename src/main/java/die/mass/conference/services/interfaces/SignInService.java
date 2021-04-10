package die.mass.conference.services.interfaces;

import die.mass.conference.dtos.request.SignInDto;
import die.mass.conference.dtos.response.TokenDto;

import java.util.Optional;

public interface SignInService {

	Optional<TokenDto> signIn(SignInDto signInDto);
}