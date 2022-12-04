package np.edu.presidential.bdd.steps;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import np.edu.persidential.dto.AuthenticationRequest;
import np.edu.persidential.dto.AuthenticationResponse;
import np.edu.persidential.model.Contact;
import np.edu.presidential.bdd.SpringIntegrationTest;
import org.junit.Assert;

public class ContactSteps extends SpringIntegrationTest {

  private static Response response;

  private static String token;

  @Given("I am the authorized user")
  public void iAmTheAuthorizedUser() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setUsername("admin");
    authenticationRequest.setPassword("admin");

    RestAssured.baseURI = BASE_URL;
    RequestSpecification request = RestAssured.given();
    request.header("Content-Type", "application/json");

    Response response =
        request.body(new Gson().toJson(authenticationRequest)).post(AUTHENTICATION_END_POINT);
    AuthenticationResponse authenticationResponse =
        new Gson().fromJson(response.asString(), AuthenticationResponse.class);

    token = authenticationResponse.getAccessToken();
  }

  @When("Create Contact")
  public void createContact() {
    Contact contact = new Contact();
    contact.setFirstName("Peter");
    contact.setLastName("Thiel");
    contact.setAddress("USA");
    contact.setPhoneNumber(9849546999L);

    RestAssured.baseURI = BASE_URL;
    RequestSpecification request = RestAssured.given();

    request.header("Content-Type", "application/json");
    request.header(AUTHORIZATION_HEADER, TOKEN_TYPE + token);
    response = request.body(new Gson().toJson(contact)).post(CONTACT_END_POINT);
  }

  @Then("Receive the status code of {int}")
  public void receiveTheStatusCodeOf(int statusCode) {
    Assert.assertEquals(statusCode, response.getStatusCode());
  }

  @When("Fetch Contact")
  public void fetchContact() {
    RestAssured.baseURI = BASE_URL;
    RequestSpecification request = RestAssured.given();

    request.header("Content-Type", "application/json");
    request.header(AUTHORIZATION_HEADER, TOKEN_TYPE + token);
    response = request.get(CONTACT_END_POINT);
  }

  @Then("Fetch Receive the status code of {int}")
  public void fetchReceiveTheStatusCodeOf(int statusCode) {
    Assert.assertEquals(statusCode, response.getStatusCode());
  }
}
