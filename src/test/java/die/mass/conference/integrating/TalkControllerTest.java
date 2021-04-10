package die.mass.conference.integrating;

import die.mass.conference.ConferenceApplication;
import die.mass.conference.DtoUtils;
import die.mass.conference.TokenUtils;
import die.mass.conference.controllers.TalkController;
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

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferenceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TalkControllerTest extends IntegrationControllerTest{

    private static final String CREATE = TalkController.ROOT + TalkController.CREATE;
    private static final String SET_TIME = TalkController.ROOT + TalkController.SET_TIME;
    private static final String UPDATE = TalkController.ROOT + TalkController.UPDATE;
    private static final String DELETE = TalkController.ROOT + TalkController.DELETE;

    @Test
    @Order(1)
    public void createTalkBySpeaker() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(CREATE, HttpMethod.POST);
        successVerify(DtoUtils.TALK_CREATION_DTO, requestBuilder, TokenUtils.SPEAKER1_TOKEN,
                "$.result.conference", "Java Day");
    }

    @Test
    public void setTime() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(SET_TIME, HttpMethod.POST);
        successVerify(DtoUtils.FIRST_TALK_TIME_DTO, requestBuilder, TokenUtils.SPEAKER1_TOKEN,
                "$.result", false);
    }

    @Test
    public void updateTime() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(SET_TIME, HttpMethod.POST);
        successVerify(DtoUtils.SECOND_TALK_TIME_DTO, requestBuilder, TokenUtils.SPEAKER1_TOKEN,
                "$.result", true);
    }

    @Test
    public void updateTalkInfo() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(UPDATE, HttpMethod.PUT);
        successVerify(DtoUtils.TALK_UPDATING_DTO, requestBuilder, TokenUtils.SPEAKER1_TOKEN,
                "$.result.title", DtoUtils.TALK_UPDATING_DTO.getTitle());
    }

    @Test
    public void deleteTalk() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(DELETE, HttpMethod.POST);
        successVerify(DtoUtils.TALK_DELETING_DTO, requestBuilder, TokenUtils.SPEAKER1_TOKEN,
                "$.result", true);
    }
}