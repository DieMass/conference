package die.mass.conference.integrating;

import die.mass.conference.ConferenceApplication;
import die.mass.conference.DtoUtils;
import die.mass.conference.TokenUtils;
import die.mass.conference.controllers.ConferenceController;
import die.mass.conference.dtos.response.Response;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferenceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConferenceControllerTest extends IntegrationControllerTest{

    private static final String REGISTER = ConferenceController.ROOT + ConferenceController.REGISTER;
    private static final String TALKS = ConferenceController.ROOT + ConferenceController.TALKS;

    @Test
    @Order(1)
    public void getTalksByListenerBeforeRegister() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(TALKS, HttpMethod.GET);
        failVerifyWithBody(DtoUtils.VALID_CONFERENCE_DTO, requestBuilder, TokenUtils.LISTENER1_TOKEN,
                 Response.Error.Type.CONFERENCE_ACCESS_DENIED.name());
    }

    @Test
    public void registerListenerInConference() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(REGISTER, HttpMethod.POST);
        successVerify(DtoUtils.VALID_CONFERENCE_DTO, requestBuilder, TokenUtils.LISTENER1_TOKEN,
                "$.result", true);
    }

    @Test
    public void registerSpeakerInConference() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(REGISTER, HttpMethod.POST);
        failVerify(DtoUtils.VALID_CONFERENCE_DTO, requestBuilder, TokenUtils.SPEAKER1_TOKEN);
    }

    @Test
    public void registerAdminInConference() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(REGISTER, HttpMethod.POST);
        failVerify(DtoUtils.VALID_CONFERENCE_DTO, requestBuilder, TokenUtils.ADMIN_TOKEN);
    }

    @Test
    public void registerListenerInInvalidConference() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(REGISTER, HttpMethod.POST);
        failVerifyWithBody(DtoUtils.INVALID_CONFERENCE_DTO, requestBuilder, TokenUtils.LISTENER1_TOKEN,
                Response.Error.Type.ENTITY_NOT_FOUND.name());
    }

    @Test
    public void getTalksByListener() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(TALKS, HttpMethod.GET);
        successVerify(DtoUtils.VALID_CONFERENCE_DTO, requestBuilder, TokenUtils.LISTENER1_TOKEN,
                "$.result[0].title", "Spring-потрошитель");
    }
}
