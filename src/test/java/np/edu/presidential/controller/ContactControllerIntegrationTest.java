package np.edu.presidential.controller;

import com.google.gson.Gson;
import np.edu.persidential.Application;
import np.edu.persidential.dto.AuthenticationRequest;
import np.edu.persidential.dto.AuthenticationResponse;
import np.edu.persidential.dto.ContactDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
public class ContactControllerIntegrationTest {

  @Autowired private MockMvc mvc;

  private Gson gson;

  private String accessToken;

  private static final String CONTACT_END_POINT = "/api/v1/contacts";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String TOKEN_TYPE = "Bearer ";

  private static Integer id;

  @Before
  public void authenticateUser() throws Exception {
    gson = new Gson();
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setUsername("admin");
    authenticationRequest.setPassword("admin");

    ResultActions resultActions =
        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(authenticationRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());

    AuthenticationResponse authenticationResponse =
        gson.fromJson(
            resultActions.andReturn().getResponse().getContentAsString(),
            AuthenticationResponse.class);

    Assert.assertEquals("admin", authenticationResponse.getUsername());
    Assert.assertNotNull(
        "Access Token should not be null", authenticationResponse.getAccessToken());
    Assert.assertNotNull(
        "Refresh Token should not be null", authenticationResponse.getRefreshToken());

    accessToken = authenticationResponse.getAccessToken();
  }

  @Test
  @Order(1)
  public void createContact() throws Exception {
    ContactDto contactDto = new ContactDto();
    contactDto.setFirstName("Nischal");
    contactDto.setLastName("Shakya");
    contactDto.setAddress("Dallu, Kathmandu");
    contactDto.setPhoneNumber(9841567900L);

    ResultActions resultActions =
        mvc.perform(
                MockMvcRequestBuilders.post(CONTACT_END_POINT)
                    .header(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(contactDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    ContactDto response =
        gson.fromJson(
            resultActions.andReturn().getResponse().getContentAsString(), ContactDto.class);

    Assert.assertNotNull("Id should not be null", response.getId());
    Assert.assertEquals(
        contactDto.getFirstName().concat(" ").concat(contactDto.getLastName()), response.getName());
    Assert.assertEquals(contactDto.getAddress(), response.getAddress());
    Assert.assertEquals(contactDto.getPhoneNumber(), response.getPhoneNumber());

    id = response.getId();
  }

  @Test
  @Order(2)
  public void getContact() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.get(CONTACT_END_POINT)
                .header(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @Order(3)
  public void updateContact() throws Exception {
    ContactDto contactDto = new ContactDto();
    contactDto.setId(id);
    contactDto.setFirstName("Hari");
    contactDto.setLastName("Maharjan");
    contactDto.setAddress("Putalisadak, Kathmandu");
    contactDto.setPhoneNumber(9845224030L);

    ResultActions resultActions =
        mvc.perform(
                MockMvcRequestBuilders.put(CONTACT_END_POINT + "/" + id)
                    .header(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(contactDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    ContactDto response =
        gson.fromJson(
            resultActions.andReturn().getResponse().getContentAsString(), ContactDto.class);

    Assert.assertNotNull("Id should not be null", response.getId());
    Assert.assertEquals(
        contactDto.getFirstName().concat(" ").concat(contactDto.getLastName()), response.getName());
    Assert.assertEquals(contactDto.getAddress(), response.getAddress());
    Assert.assertEquals(contactDto.getPhoneNumber(), response.getPhoneNumber());
  }

  @Test
  @Order(4)
  public void deleteContact() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.delete(CONTACT_END_POINT + "/" + id)
                .header(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
