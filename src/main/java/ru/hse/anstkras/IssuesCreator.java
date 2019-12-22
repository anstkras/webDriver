package ru.hse.anstkras;

import org.openqa.selenium.WebDriver;

public class IssuesCreator {
    public static void createIssue(WebDriver webDriver, MainPage.Issue issue) {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login("root", "password");
        MainPage mainPage = new MainPage(webDriver);
        mainPage.createIssue(issue);
    }
}
