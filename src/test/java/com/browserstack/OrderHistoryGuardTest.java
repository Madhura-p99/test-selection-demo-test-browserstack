package com.browserstack;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderHistoryGuardTest extends BaseUiTest {

    @Test
    public void orderHistoryRouteIsGuarded() {
        openOrdersPage();
        Assert.assertTrue(driver.findElement(By.id("login-submit")).isDisplayed());
    }
}
