package com.google.step_definitions;

import com.google.utilities.Driver;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    // CukesRunner class will see Hooks class because of glue

    // imported from cucumber not junit
    @Before(value = "@login", order = 1) // will only apply to the scenarios that have the @login tag
    public void setupLoginScenario() {
        System.out.println("--Setting up browser with further details...");
    }

    @Before
    public void setupScenario() {
        System.out.println("--Setting up browser with further details...");
    }

    @After
    public void teardownScenario(Scenario scenario) {

        // IF SCENARIO FAILS THAN TAKE SCREENSHOT
        // scenario.isFailed(); --> if scenario fails: returns true
        if(scenario.isFailed()) {

            // HOW TO TAKE A SCREENSHOT
            byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());

        }



        Driver.closeDriver();
        System.out.println("AFTER--Teardown steps are being applied");

    }

    @BeforeStep
    public void setupStep() {
        System.out.println("---setup applying for each step");
    }

    @AfterStep
    public void tearDownStep() {
        System.out.println("---teardown applying for each step");
        // take screen shot - good for evidence
    }

}
