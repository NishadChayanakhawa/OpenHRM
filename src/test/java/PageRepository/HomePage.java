package PageRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class HomePage {
	@FindBy(xpath="//a[text()='Logout']")
	WebElement objLogout;
	WebDriver objDriver;
	WebDriverWait objWait;
	Properties objProperties;
	final String[] strMenuLinkClass={"l1_link","l2_link","companyinfo"};
	public HomePage(WebDriver objDriver,int iWait) {
		this.objDriver=objDriver;
		this.objWait=new WebDriverWait(this.objDriver,iWait);
		PageFactory.initElements(this.objDriver,this);
		try {
			String strPropertiesFilePath="./src/test/resources/Links.properties";
			File objFile=new File(strPropertiesFilePath);
			FileInputStream objStream=new FileInputStream(objFile);
			this.objProperties=new Properties();
			this.objProperties.load(objStream);
			objStream.close();
		}
		catch(IOException e) {
			System.out.println("Properties file error");
		}
	}
	
	public Boolean hasHomePageLoaded() {
		Boolean hasHomePageLoaded;
		try {
			objWait.until(ExpectedConditions.visibilityOf(objLogout));
			hasHomePageLoaded=true;
		}
		catch(TimeoutException e) {
			hasHomePageLoaded=false;
		}
		return hasHomePageLoaded;
	}
	
	public String getLinkHREF(String strLinkText) {
		String strLinkHREF;
		String strXPath="//span[text()='"+strLinkText+"']/parent::a";
		System.out.println("["+strXPath+"]");
		try {
			WebElement objLink=objWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXPath)));
			strLinkHREF=objLink.getAttribute("HREF");
		}
		catch (TimeoutException e) {
			strLinkHREF="ERROR: Link with text [" +strLinkText+"] not found.";
		}
		return strLinkHREF;
	}
	
	public void logout() {
		this.objLogout.click();
	}
	
	public void hoverOverLinks(String strLinks) {
		//System.out.println(strLinks);
		WebElement[] objLinks=getLinkElements(strLinks);
		Actions objBuilder=new Actions(this.objDriver);
		for (WebElement objLink:objLinks) {
			objBuilder.moveToElement(objLink);
		}
		objBuilder.build().perform();
	}
	
	public String getVisibilityStatusOfLinks(String strLinks) {
		Boolean allVisible=true;
		Boolean allInVisible=true;
		for (String strLink:getArrayOfStrings(strLinks)) {
			WebElement objLink=this.objDriver.findElement(By.xpath(this.objProperties.getProperty(strLink)));
			allVisible=allVisible && objLink.isDisplayed();
			allInVisible=allInVisible && (!objLink.isDisplayed());
		}
		if (allVisible && (!allInVisible)) {
			return "All Visible";
		}
		else if (allInVisible && (!allVisible)) {
			return "All Invisible";
		}
		else {
			return "ERROR: Undetermined";
		}
	}
	
	private WebElement[] getLinkElements(String strLinks) {
		String[] strArrayOfLinks=getArrayOfStrings(strLinks);
		WebElement[] objLinks=new WebElement[strArrayOfLinks.length];
		for (int iCount=0;iCount<strArrayOfLinks.length;iCount++) {
			//System.out.println(strArrayOfLinks[iCount]);
			//System.out.println(this.objProperties.getProperty(strArrayOfLinks[iCount]));
			objLinks[iCount]=this.objDriver.findElement
					(By.xpath(this.objProperties.getProperty(strArrayOfLinks[iCount])));
		}
		return objLinks;
	}
	
	private String[] getArrayOfStrings(String strSource) {
		String[] strArrayOfStrings=strSource.split(Character.toString((char)10));
		return strArrayOfStrings;
	}
}
