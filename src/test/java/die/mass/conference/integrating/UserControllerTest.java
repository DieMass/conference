package die.mass.conference.integrating;

import die.mass.conference.ConferenceApplication;
import die.mass.conference.DtoUtils;
import die.mass.conference.TokenUtils;
import die.mass.conference.controllers.user.UserController;
import die.mass.conference.models.User;
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
public class UserControllerTest extends IntegrationControllerTest{

    private static final String ALL = UserController.ROOT + UserController.ALL;
    private static final String UPDATE = UserController.ROOT + UserController.UPDATE;
    private static final String BY_EMAIL = UserController.ROOT + UserController.BY_EMAIL;
    private static final String DELETE = UserController.ROOT + UserController.DELETE;

    @Test
    @Order(1)
    public void getAll() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(ALL, HttpMethod.GET);
        successVerify(null, requestBuilder, TokenUtils.ADMIN_TOKEN,
                "$.result[0].role", User.Role.ADMIN.name());
    }

    @Test
    public void update() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(UPDATE, HttpMethod.PUT);
        successVerify(DtoUtils.LISTENER1_USER_DTO, requestBuilder, TokenUtils.ADMIN_TOKEN,
                "$.result.id", 4);
    }

    @Test
    public void updateTalkInfo() throws Exception {
        final String email = "speaker1@gmail.com";
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(BY_EMAIL, HttpMethod.GET);
        requestBuilder.param("email", email);
        successVerify(null, requestBuilder, TokenUtils.ADMIN_TOKEN,
                "$.result.id", 2);
    }

    @Test
    public void deleteTalk() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = requestBuilder(DELETE, HttpMethod.POST);
        successVerify(DtoUtils.LISTENER2_USER_DTO, requestBuilder, TokenUtils.ADMIN_TOKEN,
                "$.result", true);
    }
}