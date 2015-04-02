package com.cucumber.HoldenApplitoolsPOC;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefs {
	protected static WebDriver driver;
	static List<String> myUniqueURLString = new ArrayList<String>();
	protected List<WebElement> myURLsList = new ArrayList<WebElement>();
	static int count = 0;
	static String myURL="http://preprod.holden.co.nz";

	@Given("^I am on Holden website$")
	public void I_am_on_holden_website() throws Throwable {
		System.out.println("@Given(\"^I am on Holden website$\")");
	}

	@When("^I browse the website using \"([^\"]*)\"$")
	public void I_browse_the_website_using_browser(String Browser)
			throws Throwable {

		if (Browser.contentEquals("Chrome")) {
			driver = new ChromeDriver();
		}
		if (Browser.contentEquals("Firefox")) {
			driver = new FirefoxDriver();
		}
		if (Browser.contentEquals("IE")) {
			driver = new InternetExplorerDriver();
		}
		System.out.println("Setting up the Browser");
	}

	@Then("^I should be able to check any variations in UI$")
	public void I_should_be_able_to_check_any_variations_in_UI()
			throws IOException, URISyntaxException {

		Eyes eyes = new Eyes();
		// This is our Unique API key,to be used sure you use it in all your
		// tests.

		eyes.setApiKey("gRwnleykyvL0c65oBjKU102Z9EsqbhLXHp7gF102a2qK6RM110");
		eyes.setHostOS("Windows 8");
		// to override the old baseline run the command below to wipe the slate
		// clean
		// eyes.setSaveFailedTests(true);


		// Start visual testing with browser viewport set to 1024x768 for
		// Firefox
		//driver = eyes.open(driver, "Applitools", "Holden DryRun POC ",new RectangleSize(1024, 768));
		// Navigation and checkpoints
		driver.get(myURL);
	//	eyes.setForceFullPageScreenshot(true);
	//	eyes.checkWindow("Home Page");
		for (int i = 0; i < myUniqueURLString.size(); i++) 
		{					
			myURLsList = driver.findElements(By.xpath("//a[contains(@href,'')]"));
			for (WebElement URL : myURLsList) 
			{

				if ((!myUniqueURLString.contains(URL.getAttribute("href")))	&& (URL.getAttribute("href") != null)&& (URL.getAttribute("href").contains(myURL)) && (!URL.getAttribute("href").isEmpty())) 
				{
					myUniqueURLString.add(myURL+URL.getAttribute("href"));
					System.out.println((myURL+URL.getAttribute("href")));
					count = count + 1;
				}

			}
			System.out.println("Browsing URL #" + (i + 1) + " & Extracted:"	+ count + " URLS from :" + myUniqueURLString.get(i));
			// System.out.println(myUniqueURLString.toString());
			count = 0;
			driver.get(myUniqueURLString.get(i));
			//eyes.setForceFullPageScreenshot(true);
			//eyes.checkWindow(myUniqueURLString.get(i));
			// Thread.sleep(1000);
			System.out.println("Current URL List Size: "+ myUniqueURLString.size());

		}
		System.out.println("Total URLs Crawled: "+ myUniqueURLString.size());
		//eyes.close(false);
		// finally {
		// Abort test in case of an unexpected error.
		eyes.abortIfNotClosed();
		// ffDriver.quit();
		driver.quit();

	}
}


