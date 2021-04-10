package die.mass.conference.controllers.user;

import die.mass.conference.dtos.request.SignInDto;
import die.mass.conference.dtos.response.Response;
import die.mass.conference.dtos.response.TokenDto;
import die.mass.conference.services.interfaces.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(SigninController.ROOT)
public class SigninController {

	public final static String ROOT = "/api/signin";
	private final SignInService signInService;

	@PostMapping
	public Response<TokenDto> login(@RequestBody SignInDto signInDto) {
		Optional<TokenDto> tokenCandidate = signInService.signIn(signInDto);
		return tokenCandidate.map(Response::new)
				.orElseGet(() -> new Response<>(Response.Error.builder()
						.type(Response.Error.Type.INVALID_LOGIN_DATA)
						.build()));
	}
}