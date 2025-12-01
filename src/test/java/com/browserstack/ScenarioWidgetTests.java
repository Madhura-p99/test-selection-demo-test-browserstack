package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioWidgetTests extends BaseUiTest {

    @Test
    public void staticIdFieldAcceptsInput() {
        openScenariosPage();
        WebElement staticField = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("static-id-field")));
        staticField.clear();
        staticField.sendKeys("ORD-2025-001");
        Assert.assertEquals(staticField.getAttribute("value"), "ORD-2025-001");
    }

    @Test
    public void nestedXpathInputIsInteractable() {
        openScenariosPage();
        WebElement nestedInput = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("xpath-input")));
        nestedInput.sendKeys("Nested input text");
        Assert.assertEquals(nestedInput.getAttribute("value"), "Nested input text");
    }

    @Test
    public void notificationButtonShowsToast() {
        openScenariosPage();
        WebElement submitButton = waitFor(5)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Submit']")));
        submitButton.click();
        WebElement toast = waitForToastWithTitle("Demo notification");
        Assert.assertTrue(toast.isDisplayed(), "Toast title should be visible");
    }

    @Test
    public void clipboardButtonShowsLinkCopiedToast() {
        openScenariosPage();
        WebElement copyButton = waitFor(5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Copy share link')]")));
        copyButton.click();
        WebElement toast = waitForToastWithTitle("Link copied");
        Assert.assertTrue(toast.isDisplayed());
    }

    @Test
    public void featureToggleChangesVisualState() {
        openScenariosPage();
        WebElement toggleButton = waitFor(5)
                .until(ExpectedConditions.elementToBeClickable(By.id("feature-toggle-btn")));
        String initialClass = toggleButton.getAttribute("class");
        toggleButton.click();
        waitFor(5).until(driver -> !toggleButton.getAttribute("class").equals(initialClass));
        Assert.assertTrue(toggleButton.getAttribute("class").contains("bg-blue-"));
    }

    @Test
    public void progressButtonsUpdateStatus() {
        openScenariosPage();
                driver.findElement(By.id("progress-btn-100"))
                .click();
        WebElement status = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("progress-status-id")));
        Assert.assertTrue(status.getText().contains("Complete"));

                driver.findElement(By.id("progress-btn-50"))
                .click();
        WebElement statusInProgress = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("progress-status-id")));
        Assert.assertTrue(statusInProgress.getText().contains("In Progress"));
    }

    private WebElement waitForToastWithTitle(String titleText) {
        return waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'font-semibold') and contains(normalize-space(), '" + titleText + "')]")));
    }
}
