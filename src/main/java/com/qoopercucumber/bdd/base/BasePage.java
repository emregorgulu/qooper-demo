package com.qoopercucumber.bdd.base;

import com.qoopercucumber.bdd.util.WaitTool;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;


public class BasePage {

    protected WebDriver driver;
    private final Logger log = LogManager.getLogger(getClass());
    protected WebDriverWait wait;
    protected WaitTool waitTool;

    public BasePage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
        waitTool = new WaitTool();
    }

    protected JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }


    /**
     * Javascript executer
     *
     * @param jsStmt = javascript code
     * @param wait   = wait true or false
     * @return = Object
     */
    public Object executeJS(String jsStmt, boolean wait) {
        return wait ? getJSExecutor().executeScript(jsStmt, "") : getJSExecutor().executeAsyncScript(jsStmt, "");
    }

    /**
     * Javascript executer
     *
     * @param script = Javascript or jQery script
     * @param obj    = Object
     */
    public void executeJS(String script, Object... obj) {
        getJSExecutor().executeScript(script, obj);
    }

    /**
     * Element null exception
     *
     * @param by    = By locator
     * @param index = element list index
     */
    public void nullElementException(By by, int... index) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ELEMENT (");
        stringBuilder.append(by);
        stringBuilder.append(",");
        stringBuilder.append(index.length > 0 ? index[0] : "");
        stringBuilder.append(") NOT EXISTS; AUTOMATION DATAS MAY BE INVALID!");
        throw new NullPointerException(stringBuilder.toString());
    }

    /**
     * find WebElement
     *
     * @param by    = By locator
     * @return = WebElement
     */
    public WebElement findElement(By by, int... index) {
        waitAll();
        untilElementPresence(by);
        if (index.length == 0) {
            highlightElement(driver.findElement(by));
            return driver.findElement(by);
        } else if (index[0] >= 0) {
            List<WebElement> elements = driver.findElements(by);
            if (!elements.isEmpty() && index[0] <= elements.size()) {
                return elements.get(index[0]);
            }
        }
        return null;
    }

    /**
     * Highlight Web Element
     *
     * @param element = Web Element
     */
    public void highlightElement(WebElement element) {
        executeJS("arguments[0].setAttribute('style', arguments[1]);", element,
                "color: red;border: 1px dashed red; border");
    }

    /**
     * find WebElement parent
     *
     * @param parent = WebElement parent
     * @param by     = Child by locator
     * @param index  = Child index
     * @return = WebElement
     */
    public WebElement findElement(WebElement parent, By by, int... index) {
        waitAll();
        if (index.length == 0) {
            log.info("Parent Element");
            return parent.findElement(by);
        } else if (index[0] >= 0) {
            List<WebElement> elements = parent.findElements(by);
            if (!elements.isEmpty() && index[0] <= elements.size()) {
                return elements.get(index[0]);
            }
        }
        return null;
    }

    /**
     * findElement parent by, by
     *
     * @param parent = By parent
     * @param by     = Child by locator
     * @param index  = Child index
     * @return = WebElement
     */
    public WebElement findElement(By parent, By by, int... index) {
        return findElement(findElement(parent), by, index);
    }

    /**
     * find WebElement List
     *
     * @param by = By locator
     * @return =  List
     */
    public List<WebElement> findElements(By by) {
        waitAll();
        return driver.findElements(by);
    }

    /**
     * scrollTo WebElement location
     *
     * @param x = x location
     * @param y = y location
     */
    public void scrollTo(int x, int y) {
        waitAll();
        String jsStmt = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(jsStmt, true);
    }

    /**
     * wait for web element present
     *
     * @param element = WebElement
     */
    public void waitForElementPresent(WebElement element) {

    }

    ;

    /**
     * wait for WebElement clickable
     *
     * @param by    = By locator
     * @param index = List element index
     */
    public void untilElementClickable(By by, int... index) {
        untilElementClickable(findElement(by, index));
    }

    ;

    /**
     * wait for WebElement clickable
     *
     * @param element = WebElement
     */
    protected void untilElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Expects the element appears
     *
     * @param by = By locator
     */
    public void untilElementAppear(By by) {
        wait.until(ExpectedConditions.visibilityOf(findElement(by)));
    }

    /**
     * Expects the element appears
     *
     * @param by    = By locator
     * @param index = List element index
     */
    public void untilElementAppear(By by, int... index) {
    }

    ;

    /**
     * Expects the element presence
     *
     * @param by = By locator
     */
    public void untilElementPresence(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Sleep Minute
     *
     * @param sleepTime = Integer time minute
     */
    public void timeUnitMinutes(int sleepTime) {
        try {
            TimeUnit.MINUTES.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Sleep Seconds
     *
     * @param sleepTime = Integer time seconds
     */
    public void timeUnitSeconds(int sleepTime) {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Double Click Element
     *
     * @param by = By locator
     */
    public void doubleClickElement(By by) {
        untilElementClickable(by);
        Actions a = new Actions(driver);
        a.doubleClick(findElement(by)).build().perform();
        timeUnitSeconds(2);
    }

    /**
     * Find Select Menu
     *
     * @param by = By locator
     * @return = Select
     */
    public Select selectOption(By by) {
        untilElementAppear(by);
        return new Select(findElement(by));
    }

    /**
     * Select menu with text click.
     *
     * @param by    = By locator.
     * @param value = Select option visible text
     */
    public void selectOptionClick(By by, String value) {
        selectOption(by).selectByVisibleText(value);
    }

    /**
     * Select menu first select get
     *
     * @param by = By lcoator
     * @return = WebElement select option
     */
    public WebElement selectOptionFirstSelect(By by) {
        return selectOption(by).getFirstSelectedOption();
    }

    /**
     * Assertion Control(True)
     *
     * @param message   = fail message
     * @param condition = assertion condition boolean
     */
    public void assertionTrue(String message, boolean condition) {
        assertTrue(condition, message);
    }

    /**
     * Assertion Control(True)
     *
     * @param condition = assertion condition boolean
     */
    public void assertionTrue(boolean condition) {
        assertTrue(condition);
    }

    /**
     * Assertion Control(False)
     *
     * @param message   = fail message
     * @param condition =  assertion condition boolean
     */
    public void assertionFalse(String message, boolean condition) {
        assertFalse(message, condition);
    }

    /**
     * Assertion Control(False)
     *
     * @param condition = assertion condition boolean
     */
    public void assertionFalse(boolean condition) {
        assertFalse(condition);
    }

    /**
     * Assertion Control(Equals)
     *
     * @param message  = fail message
     * @param expected = expected value
     * @param actual   = actual value
     */
    public void assertionEquals(String message, Object expected, Object actual) {
        assertEquals(message, expected, actual);
    }

    /**
     * Assertion Fail
     *
     * @param message = Fail message
     */
    public void assertFail(String message) {
        fail(message);
    }

    /**
     * Click web element (By by, int index(getIndex))
     *
     * @param by    = By locator
     * @param index = List element index
     */
    public void clickElement(By by, int... index) {
        clickElement(by, false, index);
    }


    /**
     * Click web element (By by, int index(getIndex)) waitforAjax
     *
     * @param by          = By locator
     * @param waitForAjax = untilAjaxComplete general true or false
     * @param index       = List element index integer
     */
    public void clickElement(By by, boolean waitForAjax, int... index) {
        WebElement element = null;
        try {
            element = findElement(by, index);
        } catch (Exception e) {
            log.error("ERROR :", e);
            assertFail("Element Not Found :" + e.getMessage());
        }
        if (element == null) {
            nullElementException(by, index);
        } else {
            if (!isElementDisplayed(by, index)) {
                scrollTo(element.getLocation().getX(), element.getLocation().getY());
            }
            waitForElementPresent(element);
            untilElementClickable(element);
            log.info("Element Clicked :" + element);
            element.click();
        }
    }

    /**
     * Click web element parent (By parent, By by, int index(getIndex))
     * waitforAjax
     *
     * @param parent      = By locator parent
     * @param by          = By locator child
     * @param waitForAjax = untilAjaxComplete general true or false
     * @param index=      List element index integer
     */
    public void clickElement(By parent, By by, boolean waitForAjax, int... index) {
        WebElement element = findElement(findElement(parent, index[0]), by, index[1]);
        if (element == null) {
            nullElementException(by, index);
        } else {
            if (!element.isDisplayed()) {
                scrollTo(element.getLocation().getX(), element.getLocation().getY());
            }
            untilElementClickable(element);
            waitForElementPresent(element);
            log.info("Element Clicked :" + element);
            element.click();
            if (waitForAjax) {
                //untilAjaxComplete()
            }
        }
    }

    /**
     * Click web element parent (By element, waitforAjax)
     *
     * @param element     = WebElement
     * @param waitForAjax = untilAjaxComplete general true or false
     */
    public void clickElement(WebElement element, boolean waitForAjax) {
        WebElement foundedElement = element;

        if (foundedElement == null) {
            // nullElementException(by, index)
        } else {
            if (!foundedElement.isDisplayed()) {
                scrollTo(foundedElement.getLocation().getX(), foundedElement.getLocation().getY());
            }
            untilElementClickable(foundedElement);
            waitForElementPresent(foundedElement);
            foundedElement.click();
            if (waitForAjax) {
                //untilAjaxComplete()
            }
        }
    }

    /**
     * Click web element parent (By parent, By by, int index(getIndex))
     * waitforAjax=false
     *
     * @param parent = By locator parent
     * @param by     = By locator child
     * @param index  = List element index
     */
    public void clickElement(By parent, By by, int... index) {
        clickElement(parent, by, false, index[0], index[1]);
    }


    /**
     * Provide data input(By by, String text, int index)
     *
     * @param by    = By locator
     * @param text  = Send keys value (String)
     * @param index = List element index
     */
    public void sendKeys(By by, String text, int... index) {
        sendKeys(by, text, false, index, false);
    }

    /**
     * Provide data input(By by, String text, int index) press enter
     *
     * @param by         = By locator
     * @param text
     * @param b
     * @param index
     * @param pressEnter
     */
    public void sendKeys(By by, String text, boolean b, int[] index, boolean pressEnter) {
        WebElement element = null;
        try {
            element = findElement(by, index);
        } catch (Exception e) {
            log.error("ERROR :", e);
            assertFail("Element Not Found :" + e.getMessage());
        }
        if (element == null) {
            nullElementException(by, index);
        } else if (element.isEnabled()) {
            untilElementClickable(element);
            log.info("Element Send Keys : " + text + "-" + element);
            element.clear();
            element.sendKeys(text);
            if (pressEnter) {
                element.sendKeys(Keys.ENTER);
            }
        }
    }

    /**
     * Checking the presence of qualification
     *
     * @param by = By locator
     * @return = Boolean element present
     */
    public boolean isElementPresent(By by) {
        return !findElements(by).isEmpty();
    }


    /**
     * Qualification to control the visibility
     *
     * @param by    = By locator
     * @param index = Element list index (int)
     * @return = Boolean element displayed
     */
    public boolean isElementDisplayed(By by, int... index) {
        try {
            log.info("Element Displayed Control : " + by);
            return findElement(by, index).isDisplayed();
        } catch (NoSuchElementException e) {
            log.debug("Element Is Not Displayed", e);
            return false;
        }
    }

    /**
     * Qualification to control the visibility
     * Overload method
     *
     * @param element = WebElement
     * @return = Boolean element displayed
     */
    public boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    /**
     * Qualification to control the enabled
     *
     * @param by    = By locator
     * @param index = Element list index
     * @return = Boolean element enabled
     */
    public boolean isElementEnabled(By by, int... index) {
        try {
            log.info("Element Enabled Control : " + by);
            return findElement(by, index).isEnabled();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Navigate to url
     *
     * @param url = String navigate url
     */
    public void navigateTo(String url) {
        log.info("Navigate to Url : " + url);
        driver.navigate().to(url);
    }

    /**
     * Get page source
     *
     * @return = String get page source
     */
    public String getPageSource() {
        return driver.getPageSource();
    }

    /**
     * Get element text
     *
     * @param by    = By locator
     * @param index = List element index (int)
     * @return = String get element text
     */
    public String getText(By by, int... index) {
        return findElement(by, index).getText();
    }

    /**
     * Text Present Page Source
     *
     * @param text = Value (String)
     * @return = Boolean true or false value in page source
     */
    public boolean isTextPresent(String text) {
        try {
            return getPageSource().contains(text);
        } catch (NullPointerException e) {
            log.error("Does Not Exists Element", e);
            return false;
        }
    }

    /**
     * Desired length random string
     *
     * @param size = Random string size (int)
     * @return = String random string
     */
    public String createRandomString(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

    protected void waitAll() {
        waitTool.waitAll();
        log.info("Wait all complete !");
    }
}
