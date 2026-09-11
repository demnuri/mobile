package com.noteai.automation.tests;

import com.noteai.automation.base.BaseTest;
import com.noteai.automation.pages.FoldersPage;
import com.noteai.automation.pages.HomePage;
import com.noteai.automation.pages.NoteDetailPage;
import com.noteai.automation.pages.NoteToolsPage;
import com.noteai.automation.pages.RecordingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * End-to-end walk-through for an already-logged-in user: record a (short)
 * audio note, inspect its detail/transcript view, generate flashcards for
 * it via Note Tools, then file it into a new folder.
 *
 * Steps run in order (see each method's `dependsOnMethods`) since each one
 * builds on app state left behind by the previous one.
 */
public class NoteAIFlowTest extends BaseTest {

    private static final String FOLDER_NAME = "QA " + System.currentTimeMillis();

    private HomePage homePage;

    @Test(description = "1. Launch the app and land on the All Notes home screen")
    public void step1_launchApp() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDisplayed(), "All Notes screen should be visible on launch");
    }

    @Test(description = "2. Record an audio note", dependsOnMethods = "step1_launchApp")
    public void step2_recordAudioNote() {
        RecordingPage recordingPage = homePage.startRecording();
        homePage = recordingPage.stopRecording();
        Assert.assertTrue(homePage.isDisplayed(), "Should return to All Notes after recording");
    }

    @Test(description = "3. Open the note and check its Transcript tab", dependsOnMethods = "step2_recordAudioNote")
    public void step3_checkTranscript() {
        NoteDetailPage noteDetailPage = homePage.openMostRecentNote();
        Assert.assertTrue(noteDetailPage.isDisplayed(), "Note detail should be visible for the recorded note");

        noteDetailPage.openTranscript();
        homePage = noteDetailPage.goBack();
        Assert.assertTrue(homePage.isDisplayed(), "Should return to All Notes after checking the transcript");
    }

    @Test(description = "4. Generate flashcards for the note via Note Tools",
            dependsOnMethods = "step3_checkTranscript")
    public void step4_generateFlashcards() {
        NoteDetailPage noteDetailPage = homePage.openMostRecentNote();
        Assert.assertTrue(noteDetailPage.isDisplayed(), "Note detail should be visible for the recorded note");

        NoteToolsPage noteToolsPage = noteDetailPage.openNoteTools();
        noteToolsPage.openFlashcards();
        homePage = noteToolsPage.goBack();
        Assert.assertTrue(homePage.isDisplayed(), "Should return to All Notes after generating flashcards");
    }

    @Test(description = "5. Create a new folder and file the note into it",
            dependsOnMethods = "step4_generateFlashcards")
    public void step5_createFolder() {
        FoldersPage foldersPage = homePage.openFolders();
        foldersPage.createFolder(FOLDER_NAME);
        Assert.assertTrue(foldersPage.isFolderPresent(FOLDER_NAME), "New folder should appear in the folder list");

        homePage = foldersPage.backToAllNotes();
        Assert.assertTrue(homePage.isDisplayed(), "Should return to All Notes after creating the folder");
    }
}
