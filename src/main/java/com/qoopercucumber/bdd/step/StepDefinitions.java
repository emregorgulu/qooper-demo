package com.qoopercucumber.bdd.step;

import com.qoopercucumber.bdd.base.BasePage;
import com.qoopercucumber.bdd.mapping.Mapper;
import com.qoopercucumber.bdd.util.Browser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.fail;


public class StepDefinitions {

    private static final Logger log = LogManager.getLogger(StepDefinitions.class);

    private Map<String, Object> dataMap = new HashMap<>();

    private Map<String, String> saveEnv = new HashMap<>();

    private BasePage bsp;

    private Browser browser = new Browser();

    private static RemoteWebDriver driver;

    private Mapper mapper = new Mapper();

    String stepName;

    @Before
    public void setUp() throws Exception {
        try {
            browser.setBrowser("DEFAULT", "101", "https://www.qooper.io/", 10, true);
            // BasePage Install
            bsp = new BasePage(getDriver());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @BeforeStep
    public void beforeStep() {
        log.info("Current Step : " + getStepName());
        //bsp.executeJS("logCommand", new String[]{stepName.replace("\"", "＂")})
    }

    @Given("^Click '(.*)' object.$")
    public void clickElement(String keyword) {



        By by = mapper.getElementBy(keyword);
        try {
            bsp.untilElementAppear(by);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            log.error(by + " not visible!");
            bsp.assertFail(keyword + ":" + by + " not visible!");
        }
        bsp.clickElement(by);
    }

    @Given("^Click '(.*)' object if exist.$")
    public void clickElementIfExist(String keyword) {

        if(bsp.isElementDisplayed(mapper.getElementBy(keyword))){

            By by = mapper.getElementBy(keyword);
            try {
                bsp.untilElementAppear(by);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                log.error(by + " not visible!");
                bsp.assertFail(keyword + ":" + by + " not visible!");
            }
            bsp.clickElement(by);
        }
        else
            return;

        }




    @Given("^The value \"(.*)\" is written in the (.*) field.$")
    public void sendKeys(String value, String object) {

            try {
                bsp.sendKeys(mapper.getElementBy(object), value);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                fail(e.getMessage());
                bsp.assertFail(e.getMessage());
            }
        }

    @Given("^ Type \"(.*)\" in the (.*) filed and search.$")
    public void sendKeysandSearch(String value, String object) {
        try {
            bsp.sendKeys(mapper.getElementBy(object), value);
            driver.findElement(mapper.getElementBy(object)).sendKeys(Keys.ENTER);
            saveEnv.put("searchedProduct",value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Check '(.*)' object is not visible.$")
    public void checkInvisible(String object) {
        try {
            bsp.assertionFalse("The checked element is visible!", bsp.isElementDisplayed(mapper.getElementBy(object)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Check '(.*)' object is visible.$")
    public void checkVisible(String object) {
        try {
            bsp.assertionTrue("Checked element is not visible!", bsp.isElementDisplayed(mapper.getElementBy(object)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }



    @Given("^Navigate to \"(.*)\" url.$")
    public void navigateToUrl(String url) {
        try {
            bsp.navigateTo(url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^The value \"(.*)\" is selected from the list (.*).")
    public void selectOptionClick(String value, String object) {
        try {
            bsp.selectOptionClick(mapper.getElementBy(object), value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Press TAB KEY.$")
    public void sendKeyTAB() {
        try {
            bsp.findElement(By.id("body")).sendKeys(Keys.TAB);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Press ENTER KEY.$")
    public void sendKeyENTER() {
        try {
            bsp.findElement(By.id("body")).sendKeys(Keys.ENTER);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Check (.*) object contains \"(.*)\" value.")
    public void assertContains(String object, String value) {
        try {

                log.info("Searched Value :" + value);
                log.info("Found Value :" + bsp.getText(mapper.getElementBy(object)));
                bsp.assertionTrue("The value you provided does not contain the found value..", bsp.getText(mapper.getElementBy(object)).contains(value));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }


    @Given("^ Wait '(.*)' seconds.$")
    public void waitForSeconds(int seconds) {
        try {
            log.info(seconds + " waiting seconds.");
            bsp.timeUnitSeconds(seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Wait '(.*)' minutes.")
    public void waitForMinutes(int minutes) {
        try {
            log.info(minutes + " waiting minutes.");
            bsp.timeUnitMinutes(minutes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Check (.*) object is selected.$")
    public void assertIsSelected(String object) {
        try {
            bsp.selectOptionFirstSelect(mapper.getElementBy(object));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }


    @Given("^Check (.*) field is not empty.")
    public void assertIsNotNull(String object) {
        try {
            bsp.assertionFalse("The object you are checking is empty!", bsp.getText(mapper.getElementBy(object)).isEmpty());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^ Checked (.*) filed is empty.")
    public void assertIsNull(String object) {
        try {
            bsp.assertionTrue("The object you are checking is not empty!", bsp.getText(mapper.getElementBy(object)).isEmpty());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^ Wait until (.*) object appear.")
    public void waitUntilElementAppear(String object) {
        bsp.untilElementAppear(mapper.getElementBy(object));
    }




    @Given("^Double click (.*) object.")
    public void doubleClick(String object) {
        try {
            bsp.doubleClickElement(mapper.getElementBy(object));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }




    @After
    public void tearDown() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception ex) {
                log.info("There was a problem closing the driver. It may be ignored.");
            }
        }
    }

    public String getStepName() {
        if (stepName != null) {
            return stepName;
        }
        return "Default Step";
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    public static void setDriver(RemoteWebDriver driver) {
        StepDefinitions.driver = driver;
    }
}
