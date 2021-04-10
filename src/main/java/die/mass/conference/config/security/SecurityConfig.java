package die.mass.conference.config.security;

import die.mass.conference.config.security.jwt.JwtAuthenticationFilter;
import die.mass.conference.config.security.jwt.JwtAuthenticationProvider;
import die.mass.conference.controllers.*;
import die.mass.conference.controllers.user.SigninController;
import die.mass.conference.controllers.user.UserController;
import die.mass.conference.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider authenticationProvider;
    @Value("${jwt.secret}")
    private String ANONYMOUS_KEY;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.logout().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers(SigninController.ROOT)
                .permitAll()
                .antMatchers(ConferenceController.ROOT + ConferenceController.REGISTER)
                .hasAnyAuthority(User.Role.LISTENER.name())
                .antMatchers(TalkController.ROOT + "/**")
                .hasAnyAuthority(User.Role.SPEAKER.name())
                .antMatchers(SpeakerController.ROOT + SpeakerController.TURN_INTO_SPEAKER,
                        UserController.ROOT + "/**")
                .hasAnyAuthority(User.Role.ADMIN.name())

        ;
        http.addFilterAt(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AnonymousAuthenticationFilter(ANONYMOUS_KEY), JwtAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AnonymousAuthenticationProvider(ANONYMOUS_KEY)).authenticationProvider(authenticationProvider);
    }
}
