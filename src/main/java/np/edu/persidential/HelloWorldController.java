package np.edu.persidential;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

  @GetMapping({"/hello-world", "/"})
  public String printHelloWorld(ModelMap modelMap) {
    modelMap.addAttribute("message", "Spring MVC project");
    return "spring_mvc";
  }
}
