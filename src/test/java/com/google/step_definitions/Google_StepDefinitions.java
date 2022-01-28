package com.google.step_definitions;

import com.google.utilities.ConfigurationReader;
import com.google.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Google_StepDefinitions {
    @Given("User is on google search page")
    public void userIsOnGoogleSearchPage() throws InterruptedException {
        String url = ConfigurationReader.getProperty("googleUrl");
        Driver.getDriver().get(url);
        Thread.sleep(3000);
    }

    @When("User searches for {string}")
    public void userSearchesFor(String item) {
        WebElement searchBox = Driver.getDriver().findElement(By.name("q"));
        searchBox.sendKeys(item, Keys.ENTER);
    }

    @Then("User should see {string} in the title")
    public void userShouldSeeInTheTitle(String expected) {
        String actualResult = Driver.getDriver().getTitle();
        Assert.assertTrue(actualResult.contains(expected));
    }
}
