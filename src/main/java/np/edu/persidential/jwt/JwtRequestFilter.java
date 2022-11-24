package np.edu.persidential.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  private final UserDetailsService inMemoryUserDetailsManager;

  /**
   * If the request has a JWT, validate it and set the authentication context
   *
   * @param request The request object
   * @param response The response object that is passed to the filter.
   * @param filterChain This is the filter chain that the request will pass through.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);
      if (StringUtils.hasText(jwt) && jwtTokenProvider.validateAccessToken(jwt)) {
        String username = jwtTokenProvider.getUserNameFromAccessToken(jwt);
        log.info("Verifying token");
        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Token verified");
      }
      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  /**
   * If the request URI is equal to /api/v1/login, then do not filter the request
   *
   * @param request The HttpServletRequest object.
   * @return A boolean value.
   */
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getRequestURI().equalsIgnoreCase("/api/v1/login");
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.replace("Bearer ", "");
    }
    return null;
  }
}
