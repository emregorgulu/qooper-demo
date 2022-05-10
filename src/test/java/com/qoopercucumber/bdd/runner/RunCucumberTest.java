package com.qoopercucumber.bdd.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        strict=true,
        features = "src/test/resources/features"
        , glue = {"com.qoopercucumber.bdd"},
        plugin = {"pretty", "html:target/cucumber"}
        )
public class RunCucumberTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
