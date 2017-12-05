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
    public void createOpenPageAndClickAllProduct() {
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

    @Test (priority = 30)
    public void clickBlouseLink() {
        By by = By.xpath("//h1[@class='h3 product-title']/a[text()='Blouse']");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();

    }

    @Test (priority = 33)
    public void saveProductName() {
        By by = By.cssSelector("#main > div.row > div:nth-child(2) > h1");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        newProductNameEtalon = webElement.getText();

    }

    @Test (priority = 36)
    public void saveProductPrice() {
        By by = By.cssSelector("#main > div.row > div:nth-child(2) > div.product-prices > div.product-price.h5 > div > span");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        String priceAndWrongSymbols = webElement.getText();
        newProductPriceEtalon = parsStringDigitComma (priceAndWrongSymbols);
    }

    @Test (priority = 40)
    public void addProductToCart() {
        By by = By.cssSelector("#add-to-cart-or-refresh > div.product-add-to-cart > div.product-quantity > div.add > button");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    @Test (priority = 50)
    public void moveToOrderLink() {
        By by = By.cssSelector("#blockcart-modal > div > div > div.modal-body > div > div:nth-child(2) > div > a");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    @Test (priority = 60)
    public void assertOneAmountProductInCart() {
        By by = By.cssSelector("input[name='product-quantity-spin']");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        String currentAmount = webElement.getAttribute("value");
        Assert.assertEquals(currentAmount, "1", "Warning! Amount of products in the cart is not ONE!");
    }

    @Test (priority = 70)
    public void assertNameProductInCart() {
        By by = By.cssSelector("span[class='product-image media-middle']>img");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        String currentName = webElement.getAttribute("alt");
        Boolean equalCurrentEtalonProductName = currentName.equalsIgnoreCase(newProductNameEtalon);
        Assert.assertTrue(equalCurrentEtalonProductName, "Warning! Name of products in the cart is not the same");
    }

    @Test (priority = 80)
    public void assertPriceProductInCart() {
        By by = By.cssSelector("span[class='product-price']>strong");
        WebElement webElement = webDriver.findElement(by);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        String priceAndWrongSymbols = webElement.getText();
        String currentPrice = parsStringDigitComma (priceAndWrongSymbols);
        Assert.assertEquals(currentPrice, newProductPriceEtalon, "Warning! Price of products in the cart is not the same");
    }




    public String parsStringDigitComma(String inString) {
        StringBuilder sb = new StringBuilder();
        char[] chars = inString.toCharArray();
        for (int j = 0, i = 0; i < chars.length; i++) {
            if ((Character.isDigit(chars[i]) | (chars[i]) == ',')) {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
