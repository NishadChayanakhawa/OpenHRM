package Commons;
import Commons.HelperClass;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import Commons.HelperClass;
import PageRepository.LoginPage;
import PageRepository.HomePage;
public class Stub {
	public static void main(String args[]) {
		Properties objProperties=getFile();
		WebDriver objDriver = HelperClass.getDriver();
		LoginPage objLoginPage=new LoginPage(objDriver,10);
		String strURL="https://s2.demo.opensourcecms.com/orangehrm/symfony/web/index.php/auth/login";
		objLoginPage.loadLoginPage(strURL);
		objLoginPage.hasLoginPageLoaded();
		objLoginPage.login("opensourcecms", "opensourcecms");
		HomePage objHomePage=new HomePage(objDriver,10);
		objHomePage.hasHomePageLoaded();
		Actions objBuilder=new Actions(objDriver);
		WebElement[] objLinks=new WebElement[2];
		objLinks[0]=objDriver.findElement(By.xpath(objProperties.getProperty("Admin")));
		objLinks[1]=objDriver.findElement(By.xpath(objProperties.getProperty("Admin.Organization")));
		for (WebElement objLink:objLinks) {
			objBuilder.moveToElement(objLink);
		}
		objBuilder.build().perform();
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			//Todo
		}
		objHomePage.logout();
		objLoginPage.hasLoginPageLoaded();
		objDriver.close();
	}
	
	private static Properties getFile() {
		File directory = new File("./");
		System.out.println(directory.getAbsolutePath());
		Properties objProperties=new Properties();
		String strPropertiesFileFullPath="./src/test/resources/Links.properties";
		//System.out.println(Stub.class.getResource(strPropertiesFileFullPath).getPath());
		File obj=new File(strPropertiesFileFullPath);
		FileInputStream objIP;
		try {
			objIP=new FileInputStream(obj);
			objProperties.load(objIP);
			objIP.close();
		}
		catch (IOException e) {
			//ToDo
		}
		return objProperties;
	}
}
