package com.qoopercucumber.bdd.util;

import com.qoopercucumber.bdd.step.StepDefinitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Browser {
    protected final Logger logger = LogManager.getLogger(Platform.class);
    private DesiredCapabilities capabilities;
    static String chrome = "chrome";

    public void setBrowser(String browserName, String browserVersion, String navigateUrl, int implicitlyWait, Boolean test) throws MalformedURLException {
            if (browserName.trim().toLowerCase().equals("default")) {
                capabilities = new DesiredCapabilities();
                ChromeOptions option = new ChromeOptions();
                option.addArguments("test-type");
                option.addArguments("disable-popup-blocking");
                option.addArguments("ignore-certificate-errors");
                option.addArguments("disable-translate");
                option.addArguments("start-maximized");
                option.addArguments("disable-automatic-password-saving");
                option.addArguments("allow-silent-push");
                option.addArguments("--disable-extensions");
                option.addArguments("disable-infobars");
                option.setExperimentalOption("w3c", false);

                capabilities.setCapability("browserName", chrome);
                capabilities.setPlatform(Platform.MAC);

                capabilities.setBrowserName("chrome");
                capabilities.setVersion("80");

                selectPath(Platform.MAC);
                capabilities.setCapability(ChromeOptions.CAPABILITY, option);
                StepDefinitions.setDriver(new ChromeDriver(option));
            }
        StepDefinitions.getDriver().manage().timeouts().setScriptTimeout(2, TimeUnit.MINUTES);
        StepDefinitions.getDriver().manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
        StepDefinitions.getDriver().manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        StepDefinitions.getDriver().navigate().to(navigateUrl);
    }

    protected void selectPath(Platform platform) {
        String browser;
        if (chrome.equalsIgnoreCase(capabilities.getBrowserName())) {
            browser = "webdriver.chrome.driver";
            switch (platform) {
                case MAC:
                    System.setProperty(browser, "properties/driver/chromedriver_mac");
                    break;
                case WIN10:
                case WIN8:
                case WIN8_1:
                case WINDOWS:
                    System.setProperty(browser, "properties/driver/chromedriver_win.exe");
                    break;
                case LINUX:
                    System.setProperty(browser, "properties/driver/chromedriverlinux64.exe");
                    break;
                default:
                    logger.info("PLATFORM DOES NOT EXISTS");
                    break;
            }
        } else {
            browser = "webdriver.ie.driver";
            System.setProperty(browser, "properties/driver/IEDriverServer.exe");
        }

    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(DesiredCapabilities capabilities) {
        this.capabilities = capabilities;
    }
}
