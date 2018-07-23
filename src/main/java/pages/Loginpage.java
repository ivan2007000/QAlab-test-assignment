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
		//���� ����� ������������
	public void setUserName(String strUserName) throws Exception{
		driver.findElement(userName).sendKeys(strUserName);
	}
	//���� ������
	public void setPassword(String strPassword) throws Exception{
	 driver.findElement(password).sendKeys(strPassword);}
	//������ �����
	public void clickLogin(){driver.findElement(login).click();}
	// ����� �������
	  public void checkAlert() {
	        try {
	            driver.switchTo().alert().accept();
	           System.out.println("Alert has been found and accepted.");
	        } catch (NoAlertPresentException e) {}
	    }
	  //������ ������ � �����
	public void loginToMail(String strUserName, String strPassword) throws Exception{
		this.setUserName(strUserName);
		this.setPassword(strPassword);
		clickLogin();
		checkAlert();
		}
}