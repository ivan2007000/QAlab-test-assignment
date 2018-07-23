package pages;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {

	
	WebDriver driver;
	//�������� ��������� �� ��������
	By homePageName = By.id("PH_user-email");
	By letterbutton = By.xpath("//span[contains(text(), '�������� ������')]");
	By contact = By.xpath("//div[@data-blockid='compose_to']/div");
	By theme = By.xpath("//input[@name='Subject']");
	By lettertext = By.xpath("//body[@contenteditable='true']");
	By lettersend = By.xpath("//span[contains(text(), '���������')]");
	By lettersuccess = By.xpath("//div[@class='message-sent__title']");
	
	public HomePage(WebDriver driver){
		this.driver = driver;
	}
	
	// ����� ����� �� ��������
		public String getHomePageDashboardName(){
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(homePageName));
		 return	driver.findElement(homePageName).getText();
		}
		// ���������� ���� "����"
		public void setcontact(String strcontact) throws Exception{
			Actions builder = new Actions(driver);
			WebElement element = driver.findElement(contact);
			builder.moveToElement(element).click().sendKeys(strcontact).perform();
			}
		//���������� ���� "���� ���������"
		public void settheme(String strtheme) throws Exception{
			 
			driver.findElement(theme).sendKeys(strtheme);}
		//���������� "����" ������
		public void setlettertext(String strlettertext) throws Exception{
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(lettertext));
			driver.findElement(lettertext).sendKeys(strlettertext);}
		// ������ �� ��������
		public void clicknewletter(){driver.findElement(letterbutton).click();}
		public void sendletter(){driver.findElement(lettersend).click();}
		// ����� �������� � �������� ������
		public void createNewLetter(String strcontact, String strtheme, String strlettertext ) throws Exception{
			clicknewletter();
			this.setcontact(strcontact);
			Thread.sleep(500);
			this.settheme(strtheme);
			Thread.sleep(500);
			WebElement frame = driver.findElement(By.xpath("//iframe[@title='{#aria.rich_text_area}']"));
			driver.switchTo().frame(frame);
		
			this.setlettertext(strlettertext);
			driver.switchTo().defaultContent();
			sendletter();
			}
		//�������� ������� �������� ������
		public String getsuccesssendstatus(){
			 return	driver.findElement(lettersuccess).getText();
			}
	}