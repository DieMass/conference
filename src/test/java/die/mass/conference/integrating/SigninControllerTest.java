package die.mass.conference.integrating;

import com.google.gson.Gson;
import die.mass.conference.ConferenceApplication;
import die.mass.conference.DtoUtils;
import die.mass.conference.TokenUtils;
import die.mass.conference.controllers.user.SigninController;
import die.mass.conference.dtos.request.SignInDto;
import die.mass.conference.dtos.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferenceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SigninControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private Gson gson;

    @Test
    public void verifySignin_AdminUser() throws Exception {
        successVerify(DtoUtils.ADMIN_SIGN_IN_DTO, TokenUtils.ADMIN_TOKEN);
    }

    @Test
    public void verifySignin_SpeakerUser() throws Exception {
        successVerify(DtoUtils.SPEAKER1_SIGN_IN_DTO, TokenUtils.SPEAKER1_TOKEN);
    }

    @Test
    public void verifySignin_ListenerUser() throws Exception {
        successVerify(DtoUtils.LISTENER1_SIGN_IN_DTO, TokenUtils.LISTENER1_TOKEN);
    }

    @Test
    public void verifySignin_ListenerUser_IncorrectLogin() throws Exception {
        failVerify(DtoUtils.LISTENER1_SIGN_IN_DTO_INCORRECT_LOGIN);
    }

    @Test
    public void verifySignin_ListenerUser_IncorrectPassword() throws Exception {
        failVerify(DtoUtils.LISTENER1_SIGN_IN_DTO_INCORRECT_PASSWORD);
    }

    private void successVerify(SignInDto signInDto, String token) throws Exception {
        defaultResultActions(signInDto)
                .andExpect(jsonPath("$.result.accessToken", is(token)));
    }

    private void failVerify(SignInDto signInDto) throws Exception {
        defaultResultActions(signInDto)
                .andExpect(jsonPath("$.error.type", is(Response.Error.Type.INVALID_LOGIN_DATA.name())));
    }

    private ResultActions defaultResultActions(SignInDto signInDto) throws Exception {
        return mvc.perform(post(SigninController.ROOT)
                .content(gson.toJson(signInDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
