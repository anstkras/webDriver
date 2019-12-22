package ru.hse.anstkras;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private static final String ISSUE_SUMMARY_ID = "id_l.I.ic.icr.it.issSum";
    private static final String ISSUE_DESCRIPTION_ID = "id_l.I.ic.icr.d.description";
    private static final String SUMMARY_EDIT_ID1 = "id_l.D.ni.ei.eit.summary";
    private static final String DESCRIPTION_EDIT_ID1 = "id_l.D.ni.ei.eit.description";
    private static final String CREATE_BUTTON_ID1 = "id_l.D.ni.ei.submitButton_74_0";
    private static final String SUMMARY_EDIT_ID2 = "id_l.I.ni.ei.eit.summary";
    private static final String DESCRIPTION_EDIT_ID2 = "id_l.I.ni.ei.eit.description";
    private static final String CREATE_BUTTON_ID2 = "id_l.I.ni.ei.submitButton_74_0";
    private WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void clickIssues() {
        waitPageLoad();
        var issuesButton = webDriver.findElement(By.xpath("//*[text()='" + "Issues" + "']"));
        issuesButton.click();
    }

    public void clickIssueBySummary(String summary) {
        waitPageLoad();
        var issue = webDriver.findElement(By.xpath("//*[text()='" + summary + "']"));
        issue.click();
    }

    public void clickLastIssue() {
        waitPageLoad();
        waitElement(By.className("issueId"));
        var issue = webDriver.findElement(By.className("issueId"));
        issue.click();
    }

    public Issue getIssue() {
        waitPageLoad();
        waitElement(By.id(ISSUE_SUMMARY_ID));
        waitElement(By.id(ISSUE_DESCRIPTION_ID));
        String summary = webDriver.findElement(By.id(ISSUE_SUMMARY_ID)).getText();
        String description = webDriver.findElement(By.id(ISSUE_DESCRIPTION_ID)).getText();
        return new Issue(summary, description);
    }

    public void createIssue(Issue issue) {
        waitPageLoad();
        webDriver.findElement(By.xpath("//*[text()='" + "Create Issue" + "']")).click();
        waitPageLoad();
        try {
            webDriver.findElement(By.id(SUMMARY_EDIT_ID1)).sendKeys(issue.getSummary());
        } catch (Exception e) {
            webDriver.findElement(By.id(SUMMARY_EDIT_ID2)).sendKeys(issue.getSummary());
        }
        try {
            webDriver.findElement(By.id(DESCRIPTION_EDIT_ID1)).sendKeys(issue.getDescription());
        } catch (Exception e) {
            webDriver.findElement(By.id(DESCRIPTION_EDIT_ID2)).sendKeys(issue.getDescription());
        }
        try {
            webDriver.findElement(By.id(CREATE_BUTTON_ID1)).click();
        } catch (Exception e) {
            webDriver.findElement(By.id(CREATE_BUTTON_ID2)).click();
        }
    }

    private void waitElement(By by) {
        new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void waitPageLoad() {
        new WebDriverWait(webDriver, 20).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public static class Issue {
        private String summary;
        private String description;

        public Issue(String summary, String description) {
            this.summary = summary;
            this.description = description;
        }

        public String getSummary() {
            return summary;
        }

        public String getDescription() {
            return description;
        }
    }
}
