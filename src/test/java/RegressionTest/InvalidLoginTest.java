package RegressionTest;
import Commons.HelperClass;
import org.testng.ITestContext;
import PageRepository.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.testng.Assert;
public class InvalidLoginTest {
	WebDriver objDriver;
	LoginPage objLoginPage;
	//final static String strLogPrefix="";
	//final String strURL="https://s2.demo.opensourcecms.com/orangehrm/symfony/web/index.php/auth/login";
	
	@BeforeTest(alwaysRun=true,groups={"HighPriority"})
	public void loadBrowser() {
		//System.out.println(strLogPrefix + "* Executing RegressionTest.InvalidLoginTest.loadBrowser");
		//System.out.println(strLogPrefix + "  -Loading browser");
		objDriver=HelperClass.getDriver();
		objLoginPage=new LoginPage(objDriver,10);
		//System.out.println(strLogPrefix + "   .........Loaded");
	}
	
	@BeforeMethod(alwaysRun=true,groups={"HighPriority"})
	@Parameters({"URL"})
	public void loadLoginPage(String strURL) {
		//System.out.println(strLogPrefix + "* Executing RegressionTest.InvalidLoginTest.loadLoginPage");
		//System.out.println(strLogPrefix + "  -Loading login page");
		Boolean hasLoginPageLoaded=objLoginPage.loadLoginPage(strURL);
		//System.out.println(strLogPrefix + "  -Is loading complete: ["+hasLoginPageLoaded+"]");
		Assert.assertTrue(hasLoginPageLoaded);
	}
	
	@Test(priority=0,
			groups={"HighPriority"},
			dataProvider="TestDataRepository",
			dataProviderClass=TestData.TestData.class)
	public void invalidLoginTest
	(String strTestCaseName,String strUsername, String strPassword, String stsExpectedMessage,
			ITestContext objTestContext) {
		//System.out.println(strLogPrefix + "* Executing RegressionTest.InvalidLoginTest.invalidLoginTest");
		//System.out.println(strLogPrefix + "  -Logging in");
		//objLoginPage.login(strUsername,strPassword);
		objLoginPage.login(strUsername,strPassword);
		String strErrorMessageActual=objLoginPage.getErrorMessage();
		//System.out.println(strLogPrefix + "  -Error Message: ["+strErrorMessage+"]");
		Assert.assertEquals(strErrorMessageActual,stsExpectedMessage);
	}
	
	@AfterTest(alwaysRun=true)
	public void closeBrowser() {
		//System.out.println(strLogPrefix + "* Executing RegressionTest.InvalidLoginTest.closeBrowser");
		//System.out.println(strLogPrefix + "  -Closing browser");
		objDriver.close();
		//System.out.println(strLogPrefix + "   .........Closed");
	}
}
