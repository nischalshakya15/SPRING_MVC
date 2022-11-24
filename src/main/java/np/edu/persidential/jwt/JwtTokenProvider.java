package np.edu.persidential.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${jwt.refreshToken.secretKey}")
  private String refreshTokenSecretKey;

  @Value("${jwt.accessToken.secretKey}")
  private String accessTokenSecretKey;

  @Value("${jwt.refreshToken.expiresIn}")
  private Long refreshTokenExpireInMs;

  @Value("${jwt.accessToken.expiresIn}")
  private Long accessTokenExpireInMs;

  /**
   * It generates a token for the user, with the given expiry time, and the given secret key
   *
   * @param authentication The authentication object that contains the user's details.
   * @return A JWT token
   */
  public String generateAccessToken(Authentication authentication) {
    return getToken(
        authentication,
        accessTokenExpireInMs,
        "Access token expiry date {} of user {} ",
        accessTokenSecretKey);
  }

  /**
   * It generates a token using the authentication object, the refresh token expiry time, the
   * refresh token secret key, and a message to log
   *
   * @param authentication The authentication object that contains the user's details.
   * @return A JWT token
   */
  public String generateRefreshToken(Authentication authentication) {
    return getToken(
        authentication,
        refreshTokenExpireInMs,
        "Refresh token expiry date {} of user {} ",
        refreshTokenSecretKey);
  }

  /**
   * It creates a JWT token with the username as the subject, the current date as the issued date,
   * the current date plus the token expiry time as the expiry date, and signs it with the token
   * secret key
   *
   * @param authentication The authentication object that was created in the authentication manager.
   * @param tokenExpiryTime The time in milliseconds for which the token will be valid.
   * @param tokenMessage The message to be logged when a token is generated.
   * @param tokenSecretKEy This is the secret key that will be used to sign the token.
   * @return A JWT token
   */
  private String getToken(
      Authentication authentication,
      Long tokenExpiryTime,
      String tokenMessage,
      String tokenSecretKEy) {
    User user = (User) authentication.getPrincipal();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + tokenExpiryTime);
    log.info(tokenMessage, expiryDate, user.getUsername());
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(expiryDate)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, tokenSecretKEy)
        .compact();
  }

  /**
   * Get the user name from the access token.
   *
   * The first line of the function is the most important. It uses the Jwts.parser() method to parse the access token. The
   * parser() method takes the access token secret key as a parameter. The parseClaimsJws() method parses the access token
   * and returns a Jws object. The getBody() method returns the body of the Jws object as a Claims object
   *
   * @param accessToken The access token that you want to validate.
   * @return The subject of the token.
   */
  public String getUserNameFromAccessToken(String accessToken) {
    Claims claims =
        Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(accessToken).getBody();

    return claims.getSubject();
  }

  /**
   * It takes a refresh token, verifies it using the refresh token secret key, and returns the username associated with the
   * refresh token
   *
   * @param refreshToken The refresh token that was sent to the client.
   * @return The subject of the token.
   */
  public String getUserNameFromRefreshToken(String refreshToken) {
    Claims claims =
        Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(refreshToken).getBody();

    return claims.getSubject();
  }

  /**
   * It takes an access token, and returns true if the token is valid, and false if it is not
   *
   * @param accessToken The access token to validate.
   * @return A boolean value.
   */
  public boolean validateAccessToken(String accessToken)
      throws SignatureException, MalformedJwtException, ExpiredJwtException,
          UnsupportedOperationException, IllegalArgumentException {
    Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(accessToken);
    return true;
  }

  /**
   * It takes a refresh token, and returns true if the token is valid, and false if it is not
   *
   * @param refreshToken The refresh token that you want to validate.
   * @return A boolean value.
   */
  public boolean validateRefreshToken(String refreshToken)
      throws SignatureException, MalformedJwtException, ExpiredJwtException,
          UnsupportedOperationException, IllegalArgumentException {
    Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(refreshToken);
    return true;
  }
}
