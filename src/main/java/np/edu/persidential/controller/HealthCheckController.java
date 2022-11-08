package np.edu.persidential.controller;

import np.edu.persidential.connectionfactory.ConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class HealthCheckController {

  private final ConnectionFactory connectionFactory;

  public HealthCheckController(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  /**
   * It returns a string that is the name of the view to render
   *
   * @param model This is the model object that is used to pass data from the controller to the
   *     view.
   * @return A string
   */
  @GetMapping("/check-health")
  public String getConnection(Model model) throws SQLException, ClassNotFoundException {
    model.addAttribute("message", connectionFactory.getConnection());
    return "health";
  }
}
