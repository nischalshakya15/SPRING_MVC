package np.edu.presidential.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import np.edu.persidential.Application;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureMockMvc
@CucumberContextConfiguration
@TestPropertySource(locations = "classpath:application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
public class SpringIntegrationTest {

  protected static final String CONTACT_END_POINT = "/api/v1/contacts";

  protected static final String AUTHORIZATION_HEADER = "Authorization";

  protected static final String TOKEN_TYPE = "Bearer ";

  protected static final String AUTHENTICATION_END_POINT = "/api/v1/login";

  protected static final String BASE_URL = "http://localhost:8080";
}
