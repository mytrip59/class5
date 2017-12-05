import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.IExtraOutput;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public class BaseScript {

    // object can not be created. Use only method getConfiguredDriver
    private BaseScript() {
    }

    /**
     * @return New instance of {@link WebDriver} object. Driver type is based on passed parameters
     * to the automation project, returns {@link ChromeDriver} instance by default.
     */
    public static WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver",
                        new File(BaseScript.class.getResource("geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
                System.setProperty("webdriver.ie.driver",
                        new File(BaseScript.class.getResource("IEDriverServer.exe").getFile()).getPath());
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
                desiredCapabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                return new InternetExplorerDriver(desiredCapabilities);
            case "headless-chrome":
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(BaseScript.class.getResource("chromedriver.exe").getFile()).getPath());
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("headless");
                chromeOptions.addArguments("window-size=1000x500");
                return new ChromeDriver(chromeOptions);
            case "mobile":
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(BaseScript.class.getResource("chromedriver.exe").getFile()).getPath());
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone 6");
                ChromeOptions chromeOptionsMobile = new ChromeOptions();
                chromeOptionsMobile.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(chromeOptionsMobile);


            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(BaseScript.class.getResource("chromedriver.exe").getFile()).getPath());

                ChromeDriver chromeDriver = new ChromeDriver();
                chromeDriver.manage().window().maximize();
                return chromeDriver;
        }
    }

    public static void quiteDriver(WebDriver webDriver) {
        if (webDriver != null) webDriver.quit();
    }

    public static WebDriver getRemoteDriver(String browser) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = null;
        switch (browser) {
            case "firefox":
                desiredCapabilities = DesiredCapabilities.firefox();
            case "ie":
                desiredCapabilities = DesiredCapabilities.internetExplorer();
            case "mobile":
                    desiredCapabilities = DesiredCapabilities.android();
            case "chrome":
            default:
                desiredCapabilities = DesiredCapabilities.chrome();
        }
        return new RemoteWebDriver(new URL("127.0.0.1:4444/wd/hub"), desiredCapabilities);

    }


}

