package com.browserstack;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseUiTest {

    @Test
    public void quickSelectUserCanLogin() {
        String email = getFirstDemoUserEmail();
        loginUsingQuickSelect(email);
        openPath("/profile");
        waitFor(10).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(@class,'text-xl')]")));
        WebElement profileEmail = driver.findElement(By.xpath("//div[contains(@class,'text-gray-600')][1]"));
        Assert.assertTrue(profileEmail.getText().contains(email));
    }

    @Test
    public void loginFailsWithInvalidCredentials() {
        openPath("/login");
        driver.findElement(By.id("email-input")).sendKeys("fake@example.com");
        driver.findElement(By.id("password-input")).sendKeys("wrong-password");
        driver.findElement(By.id("login-submit")).click();
        Alert alert = waitFor(5).until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains("Login failed"));
        alert.accept();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void showPasswordToggleChangesInputType() {
        openPath("/login");
        WebElement passwordField = driver.findElement(By.id("password-input"));
        Assert.assertEquals(passwordField.getAttribute("type"), "password");
        driver.findElement(By.id("show-password")).click();
        Assert.assertEquals(passwordField.getAttribute("type"), "text");
    }

    @Test
    public void visitingLoginWhileAuthenticatedRedirectsToProfile() {
        loginWithRandomDemoUser();
        openPath("/login");
        waitFor(5).until(ExpectedConditions.urlContains("/profile"));
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/profile"));
    }

    @Test
    public void logoutFromProfileClearsSession() {
        loginWithRandomDemoUser();
        openPath("/profile");
        waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Logout')]"))).click();
        WebElement loginHeader = waitFor(5).until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Login') or contains(text(),'Welcome Back')]")));
        Assert.assertTrue(loginHeader.getText().contains("Login") || loginHeader.getText().contains("Welcome"));
    }
}
