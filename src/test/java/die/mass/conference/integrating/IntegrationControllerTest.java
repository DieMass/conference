package die.mass.conference.integrating;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class IntegrationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private Gson gson;

    protected MockHttpServletRequestBuilder requestBuilder(String url, HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return get(url);
            case POST:
                return post(url);
            case PUT:
                return put(url);
            case DELETE:
                return delete(url);
            default:
                throw new IllegalArgumentException();
        }
    }



    protected void successVerify(Object body, MockHttpServletRequestBuilder builder,
                               String auth, String jsonExpression, Object expectedResult) throws Exception {
        expectSuccess(defaultResultActions(body, builder, auth))
                .andExpect(jsonPath(jsonExpression, is(expectedResult)));
    }

    protected ResultActions failVerify(Object body, MockHttpServletRequestBuilder builder, String auth) throws Exception {
        return expectFail(defaultResultActions(body, builder, auth));
    }

    protected void failVerifyWithBody(Object body, MockHttpServletRequestBuilder builder,
                                    String auth, Object expectedResult) throws Exception {
        expectSuccess(defaultResultActions(body, builder, auth))
                .andExpect(jsonPath("$.error.type", is(expectedResult)));
    }

    protected ResultActions expectFail(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().isForbidden());
    }

    protected ResultActions expectSuccess(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    protected ResultActions defaultResultActions(Object body, MockHttpServletRequestBuilder builder,
                                               String auth) throws Exception {
        return mvc.perform(builder
                .content(gson.toJson(body))
                .header(HttpHeaders.AUTHORIZATION, auth)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
