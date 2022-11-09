package np.edu.persidential.controller;

import np.edu.persidential.connectionfactory.ConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class HealthCheckController {

  private final ConnectionFactory connectionFactory;

  public HealthCheckController(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  /**
   * This function returns a map of the connection object to the client
   *
   * @return A map with a key of "message" and a value of the connection.
   */
  @GetMapping("/check-health")
  public ResponseEntity<Map<String, Connection>> getConnection() throws SQLException {
    return ResponseEntity.ok(Map.of("message", connectionFactory.getConnection()));
  }
}
