package com.qoopercucumber.bdd.util;

import com.qoopercucumber.bdd.step.StepDefinitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class WaitTool {
    private static final Logger log = LogManager.getLogger(WaitTool.class);
    //protected JavascriptExecutor jsDriver;

    protected JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) StepDefinitions.getDriver();
    }

    /// <summary>
    /// Dom ready
    /// </summary>
    public void waitDomReady() {
        try {
            if (getJSExecutor().executeScript("return document.readyState").toString().equals("complete")) {
                return;
            }
            TimeUnit.MILLISECONDS.sleep(150);
            new WebDriverWait(StepDefinitions.getDriver(), 300) {
            }.until((ExpectedCondition<Boolean>) driverObject -> (Boolean) ((JavascriptExecutor) driverObject)
                    .executeScript("return document.readyState == complete"));
            log.info("***** DOCUMENT :" + getJSExecutor().executeScript("return document.readyState").toString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /// <summary>
    /// Wait for ajax
    /// </summary>
    public void waitAjax() {
        getJSExecutor().executeScript("var callback = arguments[arguments.length - 1];"
                + "var xhr = new XMLHttpRequest();" + "xhr.open('GET', '/Ajax_call', true);"
                + "xhr.onreadystatechange = function() {" + "  if (xhr.readyState == 4) {"
                + "    callback(xhr.responseText);" + "  }" + "};" + "xhr.send();");
    }

    public static void waitJQuery() {
        try {
            new WebDriverWait(StepDefinitions.getDriver(), 300) {
            }.until((ExpectedCondition<Boolean>) driverObject -> (Boolean) ((JavascriptExecutor) driverObject)
                    .executeScript("return jQuery.active == 0"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    //Wait Until JQuery and JS Ready
    public void waitUntilJQueryReady() {
        //First check that JQuery is defined on the page. If it is, then wait AJAX
        Boolean jQueryDefined = (Boolean) getJSExecutor().executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            timeUnit(100);
            //Wait JS Load
            waitDomReady();
            //Wait JQuery Load
            waitJQuery();

        } else {
            log.error("jQuery is not defined on this site!");
        }
    }

    //Wait for Angular Load
    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(StepDefinitions.getDriver(), 15);
        JavascriptExecutor jsExec = (JavascriptExecutor) StepDefinitions.getDriver();
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                .executeScript(angularReadyScript).toString());
        //Get Angular is Ready
        boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());
        //Wait ANGULAR until it is Ready!
        if (!angularReady) {
            log.info("ANGULAR is NOT Ready!");
            //Wait for Angular to load
            wait.until(angularLoad);
        } else {
            log.info("ANGULAR is Ready!");
        }
    }

    //Wait Until Angular and JS Ready
    public void waitUntilAngularReady() {
        //First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
        Boolean angularUnDefined = (Boolean) getJSExecutor().executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) getJSExecutor().executeScript("return angular.element(document).injector() === undefined");
            if (!angularInjectorUnDefined) {
                timeUnit(100);
                //Wait Angular Load
                waitForAngularLoad();

                //Wait JS Load
                waitDomReady();

            } else {
                log.error("Angular injector is not defined on this site!");
            }
        } else {
            log.error("Angular is not defined on this site!");
        }
    }

    protected void timeUnit(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    //ers-loading
    protected void waitDissapearErs() {
        WebDriverWait wait = new WebDriverWait(StepDefinitions.getDriver(), 300);
        wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ers-loading")));
    }

    public static boolean waitDisplayed(By by) {
        WebDriverWait wait = new WebDriverWait(StepDefinitions.getDriver(), 7);
        wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public void waitAll() {
        waitDomReady();
        waitAjax();
    }

}
