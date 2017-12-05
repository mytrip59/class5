import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver webDriver;

    private By loginInputSelector = By.id("email");
    private By passwordInputSelector = By.id("passwd");
    private By submitFormSelector = By.name("submitLogin");
//    final private String EMAIL = "webinar.test@gmail.com";
//    final private String PASSWORD = "Xcg7299bnSmMuRLp9ITw";
    // selector in the bottom of the page for waiting of loading page
    private By addModuleOnPultSelector = By.cssSelector("#dashaddons > a");


    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void open (){
        webDriver.get(Properties.getBaseAdminUrl());
    }

    public void fillLoginInput(String login){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(loginInputSelector));
        WebElement webElementEmailInput = webDriver.findElement(loginInputSelector);
        webElementEmailInput.sendKeys(login);
    }

    public void fillPasswordInput (String password){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputSelector));
        WebElement webElementPasswordInput = webDriver.findElement(passwordInputSelector);
        webElementPasswordInput.sendKeys(password);
    }

    public void clickSubmitButton(){
        WebElement submitButton = webDriver.findElement(submitFormSelector);
        submitButton.click();
    }

    public void waitLoadingLoginPage(){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(addModuleOnPultSelector));
    }
}
