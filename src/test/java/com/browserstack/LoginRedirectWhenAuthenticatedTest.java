package com.browserstack;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginRedirectWhenAuthenticatedTest extends BaseUiTest {

    @Test
    public void visitingLoginWhileAuthenticatedRedirectsToProfile() {
        loginWithRandomDemoUser();
        openPath("/login");
        waitFor(5).until(ExpectedConditions.urlContains("/profile"));
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/profile"));
    }
}
