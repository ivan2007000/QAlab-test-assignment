package test;
 
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.HomePage;
import pages.Loginpage;
 
public class Testmail {
 
	WebDriver driver;
	Loginpage objLogin;
	HomePage objHomePage;
	
	@BeforeTest
	//Íàñòðîéêà ïåðåä çàïóñêîì òåñòà
	public void setup(){
		 System.setProperty("webdriver.gecko.driver",
	             "C:\\Program Files (x86)\\geckodriver-v0.19.1-win64\\geckodriver.exe");
		  System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
		  System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://mail.ru/");
	}
//Òåñò àâòîðèçàöèè
	@Test(description="Login test",priority=0)
	public void logincorrect() throws Exception{
		//ïîäêëþ÷åíèå PageObject
		  objLogin = new Loginpage(driver);
		    objHomePage = new HomePage(driver);
	// Ëîãèí â ïî÷òó
	objLogin.loginToMail("ivan.teller", "Password123!");
	//Ïðîâåðêà ñîîòâåòñòâèÿ ëîãèíà íà ñòðàíèöå
	
	AssertJUnit.assertTrue(objHomePage.getHomePageDashboardName().contains("ivan.teller@mail.ru"));
	System.out.println("Ïîëüçîâàòåëü " +objHomePage.getHomePageDashboardName() + " óñïåøíî àâòîðèçîâàëñÿ");
	}	
	//Òåñò îòïðàâêè ïèñüìà
	@Test(description="Send letter test",priority=1)
	public void letter_send() throws Exception{
		objHomePage = new HomePage(driver);
		// çàïîëíåíèå ïîëåé ïèñüìà
		objHomePage.createNewLetter("ivan.teller@mail.ru", "Òåñòîâàÿ òåìà", "Ýòî òåñòîâîå ïèñüìî");
		//Ïðîâåðêà ñòàòóñà ïèñüìà
		AssertJUnit.assertTrue(objHomePage.getsuccesssendstatus().contains("Âàøå ïèñüìî îòïðàâëåíî"));
		
	}	
	@AfterMethod()
	// Âûâîä ñòàòóñà òåñòà + ôèêñàöèÿ ñêðèíøîòà â ìîìåíò îøèáêè
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		     switch (testResult.getStatus()) {
		    case ITestResult.SUCCESS:
		        System.out.println("======ÒÅÑÒ ÏÐÎÉÄÅÍ=====");
		        break;
		    case ITestResult.FAILURE:
		        System.out.println("======ÎØÈÁÊÀ=====");
		        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-" 
						+ Arrays.toString(testResult.getParameters()) +  ".jpg"));
		        break;
		    case ITestResult.SKIP:
		        System.out.println("======ÏÐÎÏÓÙÅÍÎ=====");
		        break;
		    default:
		        throw new RuntimeException("Íåïðàâèëüíûé ñòàòóñ òåñòà");
		    }
		   
		
	 }
	// Завершение работы драйвера
	@AfterTest()
	public void aftertest() throws IOException {
		 driver.quit();
	}
}
