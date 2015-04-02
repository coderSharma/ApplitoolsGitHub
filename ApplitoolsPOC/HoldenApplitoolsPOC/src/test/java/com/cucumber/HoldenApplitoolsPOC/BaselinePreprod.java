package com.cucumber.HoldenApplitoolsPOC;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;

@RunWith(Parameterized.class)
public class BaselinePreprod {
	protected static WebDriver driver;
	protected static String workingDir = System.getProperty("user.dir").replace("\\","/");
	String windowname;
	String myURL;
	public Calendar cal = Calendar.getInstance();
	protected static Eyes eyes = new Eyes();


	@Parameters(name = "Data Row #: {index} ( For URL {0})")
	public static Collection<Object[]> spreadsheetData() throws IOException {

		return new SpreadSheetData(new FileInputStream(workingDir+"/src/test/resource/holdenPreprodAU_URLs.xls")).getData();
	}

	public BaselinePreprod(String urlList)	 {
		this.myURL =urlList ;

	}
	@BeforeClass
	public static void Setup() {
		//+date.getMonth()+date.getDay()+date.getYear()
		
		
		driver = new ChromeDriver();
		// This is our Unique API key,to be used sure you use it in all your tests.

		eyes.setApiKey("gRwnleykyvL0c65oBjKU102Z9EsqbhLXHp7gF102a2qK6RM110");
		eyes.setHostOS("Windows 8");
		// Start visual testing with browser viewport set to 1024x768
		driver = eyes.open(driver, "Applitools", "Holden AU Live Vs Preprod Baseline",new RectangleSize(1024, 768));
	}

	@Test
	public void holdenAUApplitools() throws InterruptedException
	{
		
		driver.get(myURL);
		Thread.sleep(100);

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
		eyes.checkWindow(windowname);

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

