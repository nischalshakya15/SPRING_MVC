package np.edu.persidential.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

  private String username;

  private String accessToken;

  private String refreshToken;
}
