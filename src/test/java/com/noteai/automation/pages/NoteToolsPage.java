package com.noteai.automation.pages;

import com.noteai.automation.base.BasePage;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

/** The Note Tools screen, reached from a note's detail view. */
public class NoteToolsPage extends BasePage {

    private static final By FLASHCARD_BUTTON = xpath("//XCUIElementTypeButton[contains(@name, \"Flashcard\")]");
    private static final By FLASHCARD_PROCESSING_INDICATOR =
            xpath("//*[contains(@name, \"Creating flashcards\")]");
    private static final By BACK_BUTTON = xpath(
            "(//XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/"
                    + "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/"
                    + "XCUIElementTypeOther[2]/XCUIElementTypeButton)[1]");

    public NoteToolsPage(IOSDriver driver) {
        super(driver);
    }

    /** Generates flashcards for the note and waits for generation to finish. */
    public void openFlashcards() {
        tap(FLASHCARD_BUTTON);

        if (isDisplayed(FLASHCARD_PROCESSING_INDICATOR)) {
            wait.until(d -> !isDisplayed(FLASHCARD_PROCESSING_INDICATOR));
        }
    }

    public HomePage goBack() {
        tap(BACK_BUTTON);
        return new HomePage(driver);
    }
}
