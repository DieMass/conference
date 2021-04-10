package die.mass.conference;

import die.mass.conference.integrating.ConferenceControllerTest;
import die.mass.conference.integrating.SigninControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConferenceControllerTest.class,
        ConferenceControllerTest.class,
        SigninControllerTest.class})
public class ConferenceApplicationTests {


}
