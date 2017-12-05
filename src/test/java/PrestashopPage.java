import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PrestashopPage {
    WebDriver webDriver;

    private By allProductsLinkSelector = By.cssSelector("a[class='all-product-link pull-xs-left pull-md-right h4']");

    private By anyProductNameSelector = By.cssSelector("div[class='product-description']>h1[itemprop='name']>a");
    private By anyProductPriceSelector = By.cssSelector("div[class='product-description']>div[class='product-price-and-shipping']>span[class='price']");


    private By priceNewPoductSelector = By.cssSelector("span[itemprop='price']");
    private By amountNewPoductSelector = By.cssSelector("#product-details > div.product-quantities > span");


    // selector in the bottom of the page for waiting of loading page
    private By footerSelector = By.cssSelector("footer#footer");


    public PrestashopPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void open() {
        webDriver.get(Properties.getBaseUrl());
    }

    public void clickAllProductsLink() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(allProductsLinkSelector));
        WebElement allProductsLinkWebElement = webDriver.findElement(allProductsLinkSelector);
        allProductsLinkWebElement.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(footerSelector));

    }

    public void openCreatedPoductPage(WebElement foundWebElement) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(footerSelector));
        foundWebElement.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(amountNewPoductSelector));
    }


}
