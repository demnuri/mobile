package com.noteai.automation.pages;

import com.noteai.automation.base.BasePage;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

/** The Folders screen, reached from Home > Folders. */
public class FoldersPage extends BasePage {

    private static final By NEW_FOLDER_BUTTON = accessibilityId("New Folder");
    private static final By FOLDER_NAME_FIELD = accessibilityId("Folder name");
    private static final By DONE_BUTTON = xpath("//XCUIElementTypeStaticText[@name=\"Done\"]");
    private static final By ALL_NOTES_BUTTON = accessibilityId("All Notes");

    public FoldersPage(IOSDriver driver) {
        super(driver);
    }

    /** Creates a new folder with the given name. */
    public void createFolder(String name) {
        tap(NEW_FOLDER_BUTTON);
        typeText(FOLDER_NAME_FIELD, name);
        tap(DONE_BUTTON);
    }

    /** Folder cells are images whose name contains the folder's name (the app's own timestamped label). */
    public boolean isFolderPresent(String name) {
        By folderCell = xpath("//*[contains(@name, \"" + name + "\")]");
        return isDisplayed(folderCell);
    }

    public HomePage backToAllNotes() {
        tap(ALL_NOTES_BUTTON);
        return new HomePage(driver);
    }
}
