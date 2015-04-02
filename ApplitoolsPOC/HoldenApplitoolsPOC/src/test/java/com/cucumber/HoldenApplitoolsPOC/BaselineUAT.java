package com.cucumber.HoldenApplitoolsPOC;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;

@RunWith(Parameterized.class)
public class BaselineUAT {
	protected static WebDriver driver;
	protected static String workingDir = System.getProperty("user.dir").replace("\\","/");
	String windowname;
	String myURL ;//="http://preprod.holden.com.au";
	protected static Eyes eyes = new Eyes();


	@Parameters(name = "Data Row #: {index} ( For URL {0})")
	public static Collection<Object[]> spreadsheetData() throws IOException {

		return new SpreadSheetData(new FileInputStream(workingDir+"/src/test/resource/holdenUatAU_URLs.xls")).getData();
	}

	public BaselineUAT(String urlList)	 {
		this.myURL =urlList ;

	}
	@BeforeClass
	public static void Setup() {

		driver = new FirefoxDriver();
		// This is our Unique API key,to be used sure you use it in all your tests.

		eyes.setApiKey("gRwnleykyvL0c65oBjKU102Z9EsqbhLXHp7gF102a2qK6RM110");
		eyes.setHostOS("Windows 8");
		// Start visual testing with browser viewport set to 1024x768
		driver = eyes.open(driver, "Applitools", "Holden AU Live Vs UAT Monday_160315",new RectangleSize(1850,1200));
	}

	@Test
	public void holdenAUApplitools() throws InterruptedException
	{

		driver.get(myURL);
		Thread.sleep(2000);
		

		// to override the old baseline run the command below to wipe the slate clean
		// eyes.setSaveFailedTests(true);

		eyes.setForceFullPageScreenshot(true);
		if(myURL.contains("http://preprod.holden.com.au/cars/"))
		{
			windowname=myURL.replace("http://preprod.holden.com.au/cars/", "");
		}
		if(myURL.contains("http://www.holden.com.au/cars/"))
		{
			windowname=myURL.replace("http://www.holden.com.au/cars/", "");
		}
		if(myURL.contains("http://uat.holden.com.au/cars/"))
		{
			windowname=myURL.replace("http://uat.holden.com.au/cars/", "");
		}

		windowname=myURL.replace("http://preprod.holden.com.au/cars/", "");
		if (myURL.contains("#"))
		{
			driver.navigate().refresh();
			Thread.sleep(2000);
		}
		if (myURL.contains("#accessories"))
		{
			eyes.checkWindow(windowname+"- Interior");
			driver.findElement(By.xpath("//a[contains(.,'Exterior')]")).click();
			Thread.sleep(1500);
			eyes.checkWindow(windowname+"- Exterior");
		}
		else
		{
			eyes.checkWindow(windowname);
		}
	}
	@AfterClass
	public static void tearDown()
	{
		eyes.close(false);
		eyes.abortIfNotClosed();
		// ffDriver.quit();
		driver.quit();
	}

}

