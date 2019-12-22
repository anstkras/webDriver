package ru.hse.anstkras;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IssuesTest {
    private WebDriver webDriver;

    @BeforeEach
    void initDriver() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:8080");
    }

    @Test
    void simpleTest() {
        IssuesCreator.createIssue(webDriver, new MainPage.Issue("summary", "description"));
        var mainPage = new MainPage(webDriver);
        mainPage.clickIssues();
        mainPage.clickLastIssue();
        var issue = mainPage.getIssue();
        assertEquals("summary", issue.getSummary());
        assertEquals("description", issue.getDescription());
    }

    @Test
    // Issue with empty summary should not be created
    void emptySummary() {
        IssuesCreator.createIssue(webDriver, new MainPage.Issue("summary", "description"));
        var mainPage = new MainPage(webDriver);
        mainPage.createIssue(new MainPage.Issue("", "description"));
        mainPage.clickIssues();
        mainPage.clickLastIssue();
        var issue = mainPage.getIssue();
        assertEquals("summary", issue.getSummary());
    }

    @Test
    // Issue with white space summary should also not be created but it is
    void whiteSpaceSummary() {
        IssuesCreator.createIssue(webDriver, new MainPage.Issue("summary", "description"));
        var mainPage = new MainPage(webDriver);
        mainPage.createIssue(new MainPage.Issue("    ", "white space"));
        mainPage.clickIssues();
        mainPage.clickLastIssue();
        var issue = mainPage.getIssue();

        // Should be changed after fix
        assertNotEquals("summary", issue.getSummary());
    }

    @Test
    // Issue with empty description should be created
    void emptyDescription() {
        IssuesCreator.createIssue(webDriver, new MainPage.Issue("summary", ""));
        var mainPage = new MainPage(webDriver);
        mainPage.clickIssues();
        mainPage.clickLastIssue();
        var issue = mainPage.getIssue();

        assertEquals("summary", issue.getSummary());
        assertEquals("No description", issue.getDescription());
    }

    @Test
    // Issue should be created with trimmed whitespaces
    void differentSymbolsInSummary() {
        String summary = " --- fjlsjf 212_*&^l;d   ";
        String result = "--- fjlsjf 212_*&^l;d";
        IssuesCreator.createIssue(webDriver, new MainPage.Issue(summary, "description"));
        var mainPage = new MainPage(webDriver);
        mainPage.clickIssues();
        mainPage.clickLastIssue();
        var issue = mainPage.getIssue();

        assertEquals(result, issue.getSummary());
    }

    @Test
    void whiteSpacesNotTrimmedInDescription() {
        IssuesCreator.createIssue(webDriver, new MainPage.Issue("summary", "   description   "));
        var mainPage = new MainPage(webDriver);
        mainPage.clickIssues();
        mainPage.clickLastIssue();
        var issue = mainPage.getIssue();

        assertEquals("   description   ", issue.getDescription());
    }

    @AfterEach
    void closeDriver() {
        webDriver.quit();
    }
}