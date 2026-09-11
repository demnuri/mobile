package com.noteai.automation.base;

import com.noteai.automation.config.ConfigReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.wda.XcodeCertificate;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public abstract class BaseTest {

    protected static IOSDriver driver;
    protected static WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUpDriver() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(ConfigReader.get("device.name"))
                .setUdid(ConfigReader.get("device.udid"))
                .setPlatformVersion(ConfigReader.get("platform.version"))
                .setBundleId(ConfigReader.get("app.bundleId"))
                .setXcodeCertificate(new XcodeCertificate(
                        ConfigReader.get("wda.xcodeOrgId"), ConfigReader.get("wda.xcodeSigningId")))
                .setWdaLocalPort(ConfigReader.getInt("wda.localPort"))
                .setWdaLaunchTimeout(Duration.ofMillis(ConfigReader.getInt("wda.launchTimeout")))
                .setNewCommandTimeout(Duration.ofSeconds(ConfigReader.getInt("newCommandTimeout")))
                .setAutoAcceptAlerts(true)
                .setAllowProvisioningDeviceRegistration(true)
                .setUpdatedWdaBundleId("com.noteai.WebDriverAgentRunner");

        driver = new IOSDriver(new URL(ConfigReader.get("appium.url")), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
