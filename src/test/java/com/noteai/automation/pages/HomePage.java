package com.noteai.automation.pages;

import com.noteai.automation.base.BasePage;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

/** The "All Notes" screen: the app's home/note-list view. */
public class HomePage extends BasePage {

    private static final By ROOT = accessibilityId("All Notes");
    private static final By NEW_NOTE_BUTTON = accessibilityId("New Note");
    private static final By RECORD_AUDIO_OPTION =
            xpath("//XCUIElementTypeButton[contains(@name, \"Record Audio\")]");
    private static final By FOLDERS_BUTTON = accessibilityId("Folders");
    private static final By FIRST_NOTE_CELL = xpath("(//XCUIElementTypeImage[starts-with(@name, '✏️')])[1]");

    public HomePage(IOSDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(ROOT);
    }

    public RecordingPage startRecording() {
        tap(NEW_NOTE_BUTTON);
        tap(RECORD_AUDIO_OPTION);
        return new RecordingPage(driver);
    }

    public FoldersPage openFolders() {
        tap(FOLDERS_BUTTON);
        return new FoldersPage(driver);
    }

    /** Opens the most recently created note (assumed to be first in the list). */
    public NoteDetailPage openMostRecentNote() {
        tap(FIRST_NOTE_CELL);
        return new NoteDetailPage(driver);
    }
}
