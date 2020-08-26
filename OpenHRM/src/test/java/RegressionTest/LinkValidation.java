package RegressionTest;
import Commons.HelperClass;
import PageRepository.LoginPage;
import PageRepository.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
public class LinkValidation {
	WebDriver objDriver;
	LoginPage objLoginPage;
	HomePage objHomePage;
	
	@BeforeTest(alwaysRun=true)
	public void loadBrowser() {
		objDriver=HelperClass.getDriver();
	}
	
	@Test(priority=0,alwaysRun=true,groups= {"HighPriority","MediumPriority","LowPriority"})
	@Parameters({"URL","Username","Password"})
	public void login(String strURL,String strUsername, String strPassword,ITestContext objTestContext) {
		objLoginPage=new LoginPage(objDriver,10);
		Assert.assertTrue(objLoginPage.loadLoginPage(strURL));
		objLoginPage.login(strUsername, strPassword);
		objHomePage=new HomePage(objDriver,10);
		Assert.assertTrue(objHomePage.hasHomePageLoaded());
	}
	
	@Test(priority=1,dependsOnMethods= {"login"},
			groups= {"HighPriority","MediumPriority","LowPriority"},
			dataProvider="TestDataRepository",
			dataProviderClass=TestData.TestData.class,
			enabled=false)
	public void linkValidations(String strLinkText,String strLinkHREFExpected,
			ITestContext objTestContext) {
		String strLinkHREFActual=objHomePage.getLinkHREF(strLinkText);
		Assert.assertEquals(strLinkHREFActual, strLinkHREFExpected);
	}
	
	@Test(priority=2,dependsOnMethods= {"login"},
			groups= {"HighPriority","MediumPriority","LowPriority"},
			dataProvider="TestDataRepository",
			dataProviderClass=TestData.TestData.class)
	public void menuValidation(String strMenuLinkText,String strLinksToCheck,ITestContext objTestContext) {
		Reporter.log("Links to Hover:");
		Reporter.log(strMenuLinkText);
		Reporter.log("Links to validate:");
		Reporter.log(strLinksToCheck);
		Reporter.log("Checking if all links are invisible");
		Assert.assertEquals(objHomePage.getVisibilityStatusOfLinks(strLinksToCheck),"All Invisible");
		Reporter.log(".........COMPLETED");
		objHomePage.hoverOverLinks(strMenuLinkText);
		Reporter.log("Checking if all links are Visible");
		Assert.assertEquals(objHomePage.getVisibilityStatusOfLinks(strLinksToCheck),"All Visible");
		Reporter.log(".........COMPLETED");
	}
	
	@AfterTest(alwaysRun=true)
	public void logoutAndCloseBrowser() {
		objHomePage.logout();
		Assert.assertTrue(objLoginPage.hasLoginPageLoaded());
		objDriver.close();
	}
	
}
