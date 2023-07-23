package testcontainers.cucumber;


import testcontainers.config.ContainersConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ContainersConfig.class})
@CucumberContextConfiguration
public class CucumberSpringConfiguration {

}
