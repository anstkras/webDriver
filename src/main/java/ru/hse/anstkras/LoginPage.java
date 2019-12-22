package ru.hse.anstkras;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final static String LOGIN_ID = "id_l.L.login";
    private final static String PASSWORD_ID = "id_l.L.password";
    private final static String LOGIN_BUTTON_ID = "id_l.L.loginButton";
    private WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void login(String login, String password) {
        waitPageLoad();
        waitElement(By.id(LOGIN_ID));
        var loginElement = webDriver.findElement(By.id(LOGIN_ID));
        loginElement.sendKeys(login);
        var passwordElement = webDriver.findElement(By.id(PASSWORD_ID));
        passwordElement.sendKeys(password);
        webDriver.findElement(By.id(LOGIN_BUTTON_ID)).click();
    }

    private void waitPageLoad() {
        new WebDriverWait(webDriver, 20).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    private void waitElement(By by) {
        new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}

