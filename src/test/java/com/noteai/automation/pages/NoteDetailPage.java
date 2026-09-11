package com.noteai.automation.pages;

import com.noteai.automation.base.BasePage;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

/** The note detail screen (Overview / Transcript tabs, Note Tools entry point). */
public class NoteDetailPage extends BasePage {

    private static final By TOO_SHORT_MESSAGE =
            xpath("//XCUIElementTypeStaticText[@name=\"Audio record is too short to transcript.\"]");
    private static final By TRANSCRIPT_TAB = accessibilityId("Transcript");
    private static final By NOTE_TOOLS_BUTTON = accessibilityId("Note Tools");
    private static final By BACK_BUTTON = xpath(
            "//XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/"
                    + "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/"
                    + "XCUIElementTypeOther[2]/XCUIElementTypeButton[1]");

    public NoteDetailPage(IOSDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(TOO_SHORT_MESSAGE);
    }

    public void openTranscript() {
        tap(TRANSCRIPT_TAB);
    }

    public NoteToolsPage openNoteTools() {
        tap(NOTE_TOOLS_BUTTON);
        return new NoteToolsPage(driver);
    }

    public HomePage goBack() {
        tap(BACK_BUTTON);
        return new HomePage(driver);
    }
}
