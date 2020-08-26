package PageRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
public class LoginPage {
	WebDriver objDriver;
	WebDriverWait objWait;
	final static String strLogPrefix="	";
	
	@FindBy(xpath="//div[@id='logInPanelHeading']")
	WebElement objLoginPanel;
	
	@FindBy(xpath="//input[@id='txtUsername']")
	WebElement objUsername;
	
	@FindBy(xpath="//input[@id='txtPassword']")
	WebElement objPassword;
	
	@FindBy(xpath="//input[@id='btnLogin']")
	WebElement objLoginButton;
	
	@FindBy(xpath="//span[@id='spanMessage']")
	WebElement objErrorMessage;
	
	public LoginPage(WebDriver objDriver,int iWait) {
		//System.out.println(strLogPrefix + "* Executing PageRepository.LoginPage.LoginPage");
		//System.out.print(strLogPrefix + "  -Setting WebDriver and WebDriverWait(" + iWait + " seconds)");
		this.objDriver=objDriver;
		this.objWait=new WebDriverWait(this.objDriver,iWait);
		//System.out.println(".........Completed");
		//System.out.print(strLogPrefix + "  -Initializing elements");
		PageFactory.initElements(this.objDriver,this);
		//System.out.println(".........Completed");
	}
	
	public Boolean loadLoginPage(String strURL) {
		//System.out.println(strLogPrefix + "* Executing PageRepository.LoginPage.loadLoginPage");
		//System.out.println(strLogPrefix + "  -URL: [" + strURL + "]");
		Boolean isLoginPageLoaded=true;
		objDriver.get(strURL);
		//System.out.print(strLogPrefix + "  -Waiting for login page to load");
		try {
			objWait.until(ExpectedConditions.visibilityOf(objLoginPanel));
			//System.out.println(".........Loaded");
		}
		catch (TimeoutException e) {
			//System.out.println(".........FAILED");
			isLoginPageLoaded=false;
		}
		//System.out.println(strLogPrefix + "  -Return Value: ["+isLoginPageLoaded+"]");
		return isLoginPageLoaded;
	}
	
	public Boolean hasLoginPageLoaded() {
		Boolean hasLoginPageLoaded;
		try {
			objWait.until(ExpectedConditions.visibilityOf(objLoginPanel));
			hasLoginPageLoaded=true;
		}
		catch (TimeoutException e) {
			hasLoginPageLoaded=false;
		}
		return hasLoginPageLoaded;
	}
	
	public void login(String strUsername, String strPassword) {
		//System.out.println(strLogPrefix + "* Executing PageRepository.LoginPage.login");
		//System.out.println(strLogPrefix + "  -Username : [" + strUsername + "]");
		//System.out.println(strLogPrefix + "   Password : [" + strPassword + "]");
		Actions objBuilder=new Actions(objDriver);
		Action objLoginAction=objBuilder
				.moveToElement(objUsername)
				.click()
				.sendKeys(strUsername)
				.moveToElement(objPassword)
				.click()
				.sendKeys(strPassword)
				.moveToElement(objLoginButton)
				.click()
				.build();
		//System.out.print(strLogPrefix + "  -Performing login action");
		objLoginAction.perform();
		//System.out.println(".........Completed");
	}
	
	public String getErrorMessage() {
		String strErrorMessage=null;
		//System.out.println(strLogPrefix + "* Executing PageRepository.LoginPage.getErrorMessage");
		//System.out.print(strLogPrefix + "  -Waiting for ErrorMesage element");
		try {
			objWait.until(ExpectedConditions.visibilityOf(objErrorMessage));
			//System.out.println(".........Found");
			strErrorMessage=objErrorMessage.getText();
		}
		catch (TimeoutException e) {
			//System.out.println(".........Not Found");
		}
		//System.out.print(strLogPrefix + "  -Message: ["+strErrorMessage+"]");
		return strErrorMessage;
	}
}
