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
	//Настройка перед запуском теста
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
//Тест авторизации
	@Test(description="Login test",priority=0)
	public void logincorrect() throws Exception{
		//подключение PageObject
		  objLogin = new Loginpage(driver);
		    objHomePage = new HomePage(driver);
	// Логин в почту
	objLogin.loginToMail("ivan.teller", "Password123!");
	//Проверка соответствия логина на странице
	
	AssertJUnit.assertTrue(objHomePage.getHomePageDashboardName().contains("ivan.teller@mail.ru"));
	System.out.println("Пользователь " +objHomePage.getHomePageDashboardName() + " успешно авторизовался");
	}	
	//Тест отправки письма
	@Test(description="Send letter test",priority=1)
	public void letter_send() throws Exception{
		objHomePage = new HomePage(driver);
		// заполнение полей письма
		objHomePage.createNewLetter("ivan.teller@mail.ru", "Тестовая тема", "Это тестовое письмо");
		//Проверка статуса письма
		AssertJUnit.assertTrue(objHomePage.getsuccesssendstatus().contains("Ваше письмо отправлено"));
		
	}	
	@AfterMethod()
	// Вывод статуса теста + фиксация скриншота в момент ошибки
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		     switch (testResult.getStatus()) {
		    case ITestResult.SUCCESS:
		        System.out.println("======ТЕСТ ПРОЙДЕН=====");
		        break;
		    case ITestResult.FAILURE:
		        System.out.println("======ОШИБКА=====");
		        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-" 
						+ Arrays.toString(testResult.getParameters()) +  ".jpg"));
		        break;
		    case ITestResult.SKIP:
		        System.out.println("======ПРОПУЩЕНО=====");
		        break;
		    default:
		        throw new RuntimeException("Неправильный статус теста");
		    }
		   
		
	 }
//закрытие браузера и процесса
	@AfterTest()
	public void aftertest() throws IOException {
		 driver.quit();
	}
}
