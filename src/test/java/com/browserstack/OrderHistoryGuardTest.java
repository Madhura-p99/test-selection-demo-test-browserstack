package com.browserstack;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderHistoryGuardTest extends BaseUiTest {

    @Test
    public void orderHistoryRouteIsGuarded() {
        openPath("/orders");
        waitFor(10).until(d -> d.getCurrentUrl().contains("/login"));
        Assert.assertTrue(driver.findElement(By.id("login-submit")).isDisplayed());
    }
}
