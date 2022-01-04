package project;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
features = "Features",
glue = {"com.job.stepDefinitions"},
monochrome = true,
plugin = {"html:target/report.html"}
)

public class Runnerfile {

}
