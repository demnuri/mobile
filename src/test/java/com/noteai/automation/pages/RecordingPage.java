package com.noteai.automation.pages;

import com.noteai.automation.base.BasePage;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

/** The in-app audio recording screen, reached from Home > New Note > Record Audio. */
public class RecordingPage extends BasePage {

    private static final By CANCEL_BUTTON = xpath(
            "//XCUIElementTypeApplication[@name=\"Note AI\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/"
                    + "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/"
                    + "XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/"
                    + "XCUIElementTypeOther[2]/XCUIElementTypeButton[2]");
    private static final By DONE_BUTTON = accessibilityId("Done");
    private static final By PROCESSING_INDICATOR =
            accessibilityId("Uploading your recording Please stay on this screen open while you wait");

    public RecordingPage(IOSDriver driver) {
        super(driver);
    }

    public void cancel() {
        tap(CANCEL_BUTTON);
    }

    /** Stops the recording and waits for the app to finish uploading/processing it. */
    public HomePage stopRecording() {
        tap(DONE_BUTTON);

        if (isDisplayed(PROCESSING_INDICATOR)) {
            wait.until(d -> !isDisplayed(PROCESSING_INDICATOR));
        }
        return new HomePage(driver);
    }
}
