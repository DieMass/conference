package die.mass.conference.services.implementations;

import die.mass.conference.dtos.request.SignInDto;
import die.mass.conference.dtos.response.TokenDto;
import die.mass.conference.models.User;
import die.mass.conference.repositories.UserRepository;
import die.mass.conference.services.interfaces.SignInService;
import die.mass.conference.services.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final UserRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public Optional<TokenDto> signIn(SignInDto signInData) {
        Optional<User> customerOptional = customerRepository.findByEmail(signInData.getUsername());
        return customerOptional.flatMap(customer -> processSignIn(signInData, customer));
    }

    private Optional<TokenDto> processSignIn(SignInDto signInData, User customer) {
        if (passwordEncoder.matches(signInData.getPassword(), customer.getPassword())) {
            final String accessToken = tokenService.generateAccessToken(customer);
            return Optional.of(TokenDto.builder()
                    .accessToken(accessToken)
                    .build());
        }
        return Optional.empty();
    }
}