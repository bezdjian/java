package testcontainers.cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import testcontainers.entity.Consultant;
import testcontainers.repository.ConsultantRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConsultantRepoSteps {

  @Autowired
  private ConsultantRepository consultantRepository;
  private Consultant consultant;
  private Scenario scenario;

  @Before
  public void before(Scenario scenario) {
    this.scenario = scenario;
  }

  @Given("A consultant named {string} is saved")
  public void aConsultantNamedIsSaved(String name) {
    consultant = consultantRepository.save(
        Consultant.builder()
            .id(UUID.randomUUID())
            .name(name)
            .technology("Java")
            .build()
    );
    scenario.log("Consultant saved: " + consultant);
  }

  @When("I ask whether {string} has been saved")
  public void iAskWhetherHasBeenSaved(String name) {
    List<Consultant> consultantByName = consultantRepository.findConsultantByName(name);
    assertFalse(consultantByName.isEmpty());
    consultant = consultantByName.stream().findFirst().get();

    scenario.log("Total of {" + consultantByName.size() + "} consultants found");
    scenario.log("Consultant found: " + consultant);
  }

  @Then("It should have saved {string}")
  public void itShouldHaveSaved(String name) {
    assertEquals(name, consultant.getName());
  }
}
