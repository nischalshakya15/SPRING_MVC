package np.edu.presidential.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources",
    publish = true) // Location of Gherkin file which is also known as the feature file.
public class CucumberIntegrationTest {}
