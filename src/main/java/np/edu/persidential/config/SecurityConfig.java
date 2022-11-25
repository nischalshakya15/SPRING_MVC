package np.edu.persidential.config;

import lombok.RequiredArgsConstructor;
import np.edu.persidential.jwt.JwtRequestFilter;
import np.edu.persidential.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {

  /**
   * This function creates a new JwtRequestFilter object, which is a filter that intercepts all requests and checks for a
   * valid JWT token in the Authorization header
   *
   * @return A JwtRequestFilter object.
   */
  @Bean
  public JwtRequestFilter jwtAuthenticationFilter() {
    return new JwtRequestFilter(getJwtTokenProvider(), userDetailsService(passwordEncoder()));
  }

  /**
   * It creates a new instance of the JwtTokenProvider class.
   *
   * @return A new instance of the JwtTokenProvider class.
   */
  @Bean
  public JwtTokenProvider getJwtTokenProvider() {
    return new JwtTokenProvider();
  }

  /**
   * The BCryptPasswordEncoder is a class that implements the PasswordEncoder interface.
   *
   * <p>The PasswordEncoder interface is a Spring Security interface that defines a single method
   * called encode.
   *
   * <p>The encode method takes a String and returns a String.
   *
   * <p>The BCryptPasswordEncoder class implements the encode method by hashing the password using
   * the BCrypt hashing function.
   *
   * <p>The BCryptPasswordEncoder class also implements a method called matches that takes a raw
   * password and an encoded password and returns true if the raw password matches the encoded
   * password.
   *
   * <p>The BCryptPasswordEncoder class is a Spring Security class that is used to hash passwords.
   *
   * <p>The BCryptPasswordEncoder class implements the PasswordEncoder interface.
   *
   * <p>The PasswordEncoder interface defines a single method called encode.
   *
   * <p>The encode method takes a String and returns a String.
   *
   * <p>The BCryptPasswordEncoder class implements the encode method by
   *
   * @return A new instance of BCryptPasswordEncoder.
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * It creates two users, admin and user, with the password admin and user respectively, and the
   * role ADMIN and USER respectively
   *
   * @param bCryptPasswordEncoder This is the password encoder that we created earlier.
   * @return An instance of InMemoryUserDetailsManager
   */
  @Bean
  public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    inMemoryUserDetailsManager.createUser(
        User.withUsername("admin")
            .password(bCryptPasswordEncoder.encode("admin"))
            .roles("ADMIN")
            .build());
    inMemoryUserDetailsManager.createUser(
        User.withUsername("user")
            .password(bCryptPasswordEncoder.encode("user"))
            .roles("USER")
            .build());
    return inMemoryUserDetailsManager;
  }

  /**
   * This function returns an AuthenticationManager that is configured to use the UserDetailsService
   * and PasswordEncoder that we have configured in our application.
   *
   * @param http The HttpSecurity object that is used to configure the security of the application.
   * @param bCryptPasswordEncoder This is the password encoder that we created earlier.
   * @param userDetailsService This is the service that will be used to load the user's details.
   * @return An AuthenticationManager
   */
  @Bean
  public AuthenticationManager authManager(
      HttpSecurity http,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      UserDetailsService userDetailsService)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }

  /**
   * If the request is a GET request, permit it. If the request is a POST or DELETE request,
   * authenticate it.
   *
   * @param http This is the HttpSecurity object that is used to configure the security filter
   *     chain.
   * @return A SecurityFilterChain
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http = http.cors().and().csrf().disable();

    http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

    http = http.authorizeRequests().antMatchers("/api/v1/login").permitAll().and();

    // Authenticate request
    http = http.authorizeRequests().antMatchers("/api/v1/**").authenticated().and();

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
