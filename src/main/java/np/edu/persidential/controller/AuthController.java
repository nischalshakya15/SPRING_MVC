package np.edu.persidential.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import np.edu.persidential.dto.AuthenticationRequest;
import np.edu.persidential.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  /**
   * It takes a username and password, authenticates the user, and returns a JWT access token and
   * refresh token
   *
   * @param request The request body is a JSON object that contains the username and password.
   * @return A map with two keys: accessToken and refreshToken
   */
  @PostMapping
  public ResponseEntity<Map<String, String>> authenticate(
      @RequestBody @Valid AuthenticationRequest request) {
    log.info("Authenticating user {}", request.getUsername());
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getUsername(), request.getPassword()));

      String accessToken = jwtTokenProvider.generateAccessToken(authentication);
      String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

      return ResponseEntity.ok()
          .body(
              Map.of(
                  "accessToken",
                  accessToken,
                  "refreshToken",
                  refreshToken,
                  "username",
                  request.getUsername()));
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
