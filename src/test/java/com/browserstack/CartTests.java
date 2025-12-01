package com.browserstack;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseUiTest {

    @Test
    public void cartDisplaysItemsAddedFromCatalog() {
        addProductToCart(4);
        openCartPage();
        WebElement name = waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='cart-item']//div[contains(@class,'font-semibold')]")));
        Assert.assertTrue(name.getText().contains("Galaxy Note"));
    }

    @Test
    public void incrementAndDecrementButtonsUpdateQuantity() {
        addProductToCart(5);
        openCartPage();
        By quantityLocator = By.xpath("//div[@id='cart-item']//span[contains(@id,'item-quantity')]");
        WebElement plusButton = driver.findElement(By.xpath("//div[@id='cart-item']//button[text()='+']"));
        WebElement minusButton = driver.findElement(By.xpath("//div[@id='cart-item']//button[text()='-']"));
        plusButton.click();
        waitFor(5).until(ExpectedConditions.textToBe(quantityLocator, "2"));
        minusButton.click();
        waitFor(5).until(ExpectedConditions.textToBe(quantityLocator, "1"));
    }

    @Test
    public void clearCartButtonRemovesAllItems() {
        addProductsToCart(1, 2);
        openCartPage();
        WebElement clearButton = waitFor(5).until(ExpectedConditions.elementToBeClickable(By.id("clear-cart-btn")));
        clearButton.click();
        WebElement emptyMessage = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your cart is empty.']")));
        Assert.assertTrue(emptyMessage.isDisplayed());
    }

    @Test
    public void checkoutButtonHiddenWhenLoggedOut() {
        addProductToCart(6);
        openCartPage();
        WebElement notice = waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'text-yellow-800')]")));
        Assert.assertTrue(notice.getText().contains("Please"));
    }

    @Test
    public void removeItemLinkRemovesSingleItem() {
        addProductsToCart(7, 8);
        openCartPage();
        List<WebElement> beforeItems = driver.findElements(By.id("cart-item"));
        driver.findElement(By.xpath("(//div[@id='cart-item']//button[contains(text(),'Remove')])[1]")).click();
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.id("cart-item"), beforeItems.size() - 1));
    }

    @Test
    public void cartShowsEmptyStateForEmptyCart() {
        loginWithRandomDemoUser();
        openPath("/cart");
        WebElement empty = waitFor(5).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Your cart is empty.')]")));
        Assert.assertTrue(empty.isDisplayed());
    }

}
