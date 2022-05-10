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
            log.error(by + " görülmedi!");
            bsp.assertFail(keyword + ":" + by + " görülmedi!");
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
                log.error(by + " görülmedi!");
                bsp.assertFail(keyword + ":" + by + " görülmedi!");
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


    @Given("^(.*) alanına \"(.*)\" değeri yazılır ve aranır.$")
    public void sendKeysandSearch(String object, String value) {
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

    @Given("^(.*) karakter uzunluğunda rastgele değer \"(.*)\" değişkenine kaydedilir.")
    public void sendKeysRandom(String size, String variable) {
        try {
            saveEnv.put(variable, "0" + bsp.createRandomString(Integer.parseInt(size)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }


    @Given("^(.*) alanına \"(.*)\" değişkenine atanmış değer yazılır.$")
    public void sendKeysGetMap(String object, String keyword) {
        try {
            if (saveEnv.get(keyword) != null) {
                bsp.sendKeys(mapper.getElementBy(object), saveEnv.get(keyword));
            } else {
                fail("Verilen değişken ile ilgili kayıt bulunamamıştır.");
            }
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

    @Given("^Check '(.*)' object visible.$")
    public void checkVisible(String object) {
        try {
            bsp.assertionTrue("Checked element is not visible!", bsp.isElementDisplayed(mapper.getElementBy(object)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^(.*) objesinin değerinin \"(.*)\" değerine eşitliği kontrol edilir.$")
    public void assertEqual(String object, String value) {
        try {
            if (value.contains("@DB_")) {
                String replaceValue = value.replaceAll("@DB_", "");
                log.info("ARANILAN DEĞER :" + dataMap.get(replaceValue));
                log.info("BULUNAN DEĞER :" + bsp.getText(mapper.getElementBy(object)));
                bsp.assertionEquals("Veritabanın'dan gelen değer ile bulunan değer eşleşmiyor.", bsp.getText(mapper.getElementBy(object)), dataMap.get(replaceValue));
            } else {
                log.info("ARANILAN DEĞER :" + value);
                log.info("BULUNAN DEĞER :" + bsp.getText(mapper.getElementBy(object)));
                bsp.assertionEquals("Verilen değer ile bulunan değer eşleşmiyor.", bsp.getText(mapper.getElementBy(object)), value);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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

    @Given("^(.*) listesinden \"(.*)\" değeri seçilir.")
    public void selectOptionClick(String object, String value) {
        try {
            bsp.selectOptionClick(mapper.getElementBy(object), value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Klavyeden TAB tuşuna basılır.$")
    public void sendKeyTAB() {
        try {
            bsp.findElement(By.id("body")).sendKeys(Keys.TAB);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Klavyeden ENTER tuşuna basılır.$")
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

                log.info("ARANILAN DEĞER :" + value);
                log.info("BULUNAN DEĞER :" + bsp.getText(mapper.getElementBy(object)));
                bsp.assertionTrue("Verdiğiniz değer bulunan değeri içermiyor.", bsp.getText(mapper.getElementBy(object)).contains(value));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }


    @Given("^'(.*)' saniye beklenir.$")
    public void waitForSeconds(int seconds) {
        try {
            log.info(seconds + " saniye bekleniyor.");
            bsp.timeUnitSeconds(seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^'(.*)' dakika beklenir.")
    public void waitForMinutes(int minutes) {
        try {
            log.info(minutes + " dakika bekleniyor.");
            bsp.timeUnitMinutes(minutes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^(.*) objesinin seçili olduğu kontrol edilir.$")
    public void assertIsSelected(String object) {
        try {
            bsp.selectOptionFirstSelect(mapper.getElementBy(object));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }


    @Given("^Ürün listesinde '(.*)'. sayfaya gidilir.$")
    public void navigateProductListbyNumber(int index) {
        try {
            bsp.navigateTo("https://www.n11.com/arama?q="+saveEnv.get("searchedProduct")+"&pg="+index);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Ürün listesinden '(.*)' numaralı objeye tıklanır.$")
    public void selectProductListByIndex(int index) {
        try {
            String Product = "#view > ul > li:nth-child(" + index + ") > div > div.pro > a";
            driver.findElementByCssSelector(Product).click();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Ürün listesinden '(.*)' numaralı ürün favorilere eklenir ve '(.*)' parametresi ile ürün adı saklanır.$")
    public void addFavoritesOnListByIndex(int index, String parameter) {
        try {
            String Product = "#view > ul > li:nth-child(" + index + ") > div > div.pro > span";
            String ProductName= driver.findElementByCssSelector("#view > ul > li:nth-child(" + index + ") > div > div.pro > a > h" + index).getText();
            saveEnv.put(parameter,ProductName);
            driver.findElementByCssSelector(Product).click();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }


        @Given("^Favori listesinde '(.*)' parametresi ile saklanan ürün adının var olduğu kontrol edilir.$")
        public void checkFavoritesOnListByParameter(String parameter) {
            try {
                bsp.isTextPresent(saveEnv.get(parameter));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                fail(e.getMessage());
                bsp.assertFail(e.getMessage());
            }
    }

    @Given("^Favori listesinde '(.*)' parametresi ile saklanan ürün adının var olmadığı kontrol edilir.$")
    public void checkNonExsistFavoritesOnListByParameter(String parameter) {
        String ProductName= saveEnv.get(parameter);

        try {
            bsp.assertionFalse("Favori listesinde " + ProductName + " ürünü bulunamadı.",bsp.isTextPresent(ProductName)==false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^Favori listesinde '(.*)' parametresi ile saklanan ürün favorilerden kaldırılır.$")
    public void removeFavoritesOnListByParameter(String parameter) {
        try {


            List<String> values = new ArrayList<String>();
            List<WebElement> url_link = driver.findElements(mapper.getElementBy("FAVORI URUN LISTESI"));
            for ( WebElement we: url_link) {

                values.add(we.getText());
            }

            int ind = values.indexOf(saveEnv.get(parameter))+1;
            String ProductName= "#view > ul > li:nth-child(" + ind + ") > div > div.wishProBtns > span";
            log.info(ProductName);
            driver.findElementByCssSelector(ProductName).click();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^(.*) alanının dolu olduğu kontrol edilir.")
    public void assertIsNotNull(String object) {
        try {
            bsp.assertionFalse("Kontrol ettiğiniz obje boştur!", bsp.getText(mapper.getElementBy(object)).isEmpty());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^(.*) alanının boş olduğu kontrol edilir.")
    public void assertIsNull(String object) {
        try {
            bsp.assertionTrue("Kontrol ettiğiniz obje boş değildir!", bsp.getText(mapper.getElementBy(object)).isEmpty());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^(.*) objesi görününe kadar beklenir.")
    public void waitUntilElementAppear(String object) {
        bsp.untilElementAppear(mapper.getElementBy(object));
    }




    @Given("^(.*) objesine çift tıklanır.")
    public void doubleClick(String object) {
        try {
            bsp.doubleClickElement(mapper.getElementBy(object));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
            bsp.assertFail(e.getMessage());
        }
    }

    @Given("^(.*) objesinin aktif olduğu kontrol edilir.")
    public void checkElementIsEnabled(String object) {
        try {
            bsp.isElementEnabled(mapper.getElementBy(object));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(object + " objesi aktiflik kontrolü yapılamamıştır.");
        }
    }

    @Given("^(.*) objesinin pasif olduğu kontrol edilir.")
    public void checkElementIsDisabled(String object) {
        try {
            bsp.assertionFalse(object + " objesi aktifdir.", bsp.isElementEnabled(mapper.getElementBy(object)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(object + " objesi pasiflik kontrolü yapılamamıştır.");
        }
    }

    @Given("^(.*) objesinin değeri '(.*)' değişkenine kaydedilir.")
    public void storeVariable(String object, String parameter) {
        try {
            saveEnv.put(parameter, bsp.getText(mapper.getElementBy(object)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bsp.assertFail(object + " objesi kaydedilemedi.");
        }
    }


    @After
    public void tearDown() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception ex) {
                log.info("Driver kapatılırken sorun oluştu. Dikkate alınmayabilir.");
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
