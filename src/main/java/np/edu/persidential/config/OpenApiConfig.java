package np.edu.persidential.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  /**
   * It creates a new OpenAPI object and returns it.
   *
   * @return OpenAPI object
   */
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(
            new Info()
                .title("SPRING_MVC API DOCUMENTATION")
                .description("CONTAINS THE API FOR SPRING_MVC PROJECT")
                .contact(new Contact().email("nischalshakya95@gmail.com"))
                .license(new License().name("Presidential College"))
                .version("1.0"));
  }
}
