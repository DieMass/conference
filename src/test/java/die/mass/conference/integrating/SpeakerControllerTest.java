package die.mass.conference.integrating;

import die.mass.conference.ConferenceApplication;
import die.mass.conference.DtoUtils;
import die.mass.conference.TokenUtils;
import die.mass.conference.controllers.SpeakerController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferenceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpeakerControllerTest extends IntegrationControllerTest {

    private static final String TALKS = SpeakerController.ROOT + SpeakerController.TALKS;
    private static final String TURN_INTO_SPEAKER = SpeakerController.ROOT + SpeakerController.TURN_INTO_SPEAKER;

    @Test
    @Order(1)
    public void turnIntoSpeaker() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(TURN_INTO_SPEAKER, HttpMethod.POST);
        successVerify(DtoUtils.LISTENER1_USER_DTO, requestBuilder, TokenUtils.ADMIN_TOKEN,
                "$.result", true);
    }

    @Test
    @Order(2)
    public void getTalksOfSpeaker() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(TALKS, HttpMethod.GET);
        successVerify(null, requestBuilder, TokenUtils.SPEAKER1_TOKEN,
                "$.result", Collections.emptyList());
    }
}
