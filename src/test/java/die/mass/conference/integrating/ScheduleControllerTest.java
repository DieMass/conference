package die.mass.conference.integrating;

import die.mass.conference.ConferenceApplication;
import die.mass.conference.TokenUtils;
import die.mass.conference.controllers.ScheduleController;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConferenceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleControllerTest extends IntegrationControllerTest {

    private static final String ROOT = ScheduleController.ROOT;

    @Test
    @Order(1)
    public void getTalksByListenerBeforeRegister() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(ROOT, HttpMethod.GET);
        successVerify(null, requestBuilder, TokenUtils.LISTENER1_TOKEN,
                "$.result.roomSchedule[0].roomName", "1301");
    }
}
