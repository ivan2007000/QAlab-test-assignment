package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class Loginpage {
	
	WebDriver driver;
	By userName = By.id("mailbox:login");
	By password = By.id("mailbox:password");
	By login = By.xpath("//label[@id='mailbox:submit']/input");
		public Loginpage(WebDriver driver){
		this.driver = driver;
	}
		//Ввод имени пользователя
	public void setUserName(String strUserName) throws Exception{
		driver.findElement(userName).sendKeys(strUserName);
	}
	//Ввод пароля
	public void setPassword(String strPassword) throws Exception{
	 driver.findElement(password).sendKeys(strPassword);}
	//кнопка входа
	public void clickLogin(){driver.findElement(login).click();}
	// Поиск алертов
	  public void checkAlert() {
	        try {
	            driver.switchTo().alert().accept();
	           System.out.println("Alert has been found and accepted.");
	        } catch (NoAlertPresentException e) {}
	    }
	  //Метода логина в почту
	public void loginToMail(String strUserName, String strPassword) throws Exception{
		this.setUserName(strUserName);
		this.setPassword(strPassword);
		clickLogin();
		checkAlert();
		}
}