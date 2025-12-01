package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTests extends BaseUiTest {

    @Test
    public void heroSectionDisplaysExpectedCopy() {
        goHome();
        WebElement heroTitle = driver.findElement(By.id("hero-title"));
        WebElement heroSubtitle = driver.findElement(By.id("hero-subtitle"));
        Assert.assertTrue(heroTitle.getText().contains("Demo Playground"));
        Assert.assertTrue(heroSubtitle.getText().contains("BrowserStack-ready"));
    }

    @Test
    public void primaryCtaNavigatesToScenarios() {
        goHome();
        driver.findElement(By.id("primary-cta")).click();
        waitFor(10).until(d -> d.getCurrentUrl().contains("/scenarios"));
        Assert.assertTrue(driver.findElement(By.id("static-id-field")).isDisplayed());
    }

    @Test
    public void profileLinkRedirectsToLoginWhenLoggedOut() {
        goHome();
        driver.findElement(By.id("profile-btn")).click();
        waitFor(10).until(d -> d.getCurrentUrl().contains("/login"));
        Assert.assertEquals(driver.findElement(By.id("login-title")).getText(), "Welcome Back");
    }

    @Test
    public void orderHistoryRouteIsGuarded() {
        openPath("/orders");
        waitFor(10).until(d -> d.getCurrentUrl().contains("/login"));
        Assert.assertTrue(driver.findElement(By.id("login-submit")).isDisplayed());
    }

    @Test
    public void cartBadgeStartsAtZero() {
        goHome();
        String badgeValue = driver.findElement(By.cssSelector("#shopping-cart-btn span")).getText().trim();
        Assert.assertEquals(badgeValue, "0");
    }
}
