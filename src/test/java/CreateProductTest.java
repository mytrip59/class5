import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class CreateProductTest {
    private WebDriver driver;
    private EventFiringWebDriver webDriver;
    private LoginPage loginPage;
    private PrestashopPage prestashopPage;
    private final boolean DEBUGMODEENABLE = true;

    private String newProductNameEtalon;
    private String newProductAmountEtalon;
    private String newProductPriceEtalon;
    private WebElement foundNewProductOnFirstPageWebElement = null;

    @DataProvider(name = "getLoginPassword")
    public static Object[][] getLoginPaswordMethod() {
        String  dataProviderObject[][] = {{"webinar.test@gmail.com","Xcg7299bnSmMuRLp9ITw"}};
        return dataProviderObject;
    }

    @BeforeClass
    @Parameters ("selenium.browser")
    public void beforeClass (@Optional("mobile") String browser) throws MalformedURLException {
        driver = BaseScript.getDriver(browser);

        // driver = BaseScript.getRemoteDriver(browser);

        webDriver = new EventFiringWebDriver(driver);
        webDriver.register(new EventHandler());

        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public void afterClass (){
        BaseScript.quiteDriver(webDriver);
    }

    @Test (priority = 10)
    public void createOpenPrestashopClickAllProduct() {
        prestashopPage = new PrestashopPage(webDriver);
        prestashopPage.open();
        prestashopPage.clickAllProductsLink();
    }


    @Test  (priority = 20)
    public void checkMobileDesktopApp (){
        By by = By.cssSelector("#main > div.block-category.card.card-block.hidden-sm-down > h1");
        try {
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
            System.out.println("You use desktop browser");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("You use mobile browser");
        }
    }

    @Test (priority = 40)
    public void clickBlousePrestashop() {
        By blouseBy = By.cssSelector("#content > section > div > article:nth-child(2) > div > div.product-description > h1 > a");
        prestashopPage.openCreatedPoductPage(webDriver.findElement(blouseBy));

    }
}
