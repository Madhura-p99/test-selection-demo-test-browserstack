package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductCatalogTests extends BaseUiTest {

    @Test
    public void searchFiltersResults() {
        openProductsPage();
        WebElement searchInput = driver.findElement(By.cssSelector("input[placeholder='Search products...']"));
        searchInput.clear();
        searchInput.sendKeys("pixel");
        waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(By.id("product-card-1")));
        int visibleCards = driver.findElements(By.cssSelector("[id^='product-card-']"))
            .size();
        Assert.assertEquals(visibleCards, 1, "Filtered catalog should show exactly 1 result");
    }
    
    @Test
    public void catalogDefaultShowsAllProducts() {
        openProductsPage();
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[id^='product-card-']"), 10));
        int totalCards = driver.findElements(By.cssSelector("[id^='product-card-']")).size();
        Assert.assertEquals(totalCards, 10, "Default catalog load should render 10 products");
    }

    @Test
    public void categoryFilterLimitsResultsForIOS() {
        openProductsPage();
        Select categorySelect = new Select(driver.findElement(By.cssSelector("select")));
        categorySelect.selectByValue("ios");
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.id("product-card-1"), 0));  // Pixel phone
        int visibleCards = driver.findElements(By.cssSelector("[id^='product-card-']"))
            .size();
        Assert.assertEquals(visibleCards, 2, "Filtered catalog should show exactly two results");
    }

        @Test
    public void categoryFilterLimitsResultsForAndroid() {
        openProductsPage();
        Select categorySelect = new Select(driver.findElement(By.cssSelector("select")));
        categorySelect.selectByValue("android");
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.id("product-card-2"), 0)); // iPhone
        int visibleCards = driver.findElements(By.cssSelector("[id^='product-card-']"))
            .size();
        Assert.assertEquals(visibleCards, 8, "Filtered catalog should show exactly 8 results");
    }

    @Test
    public void addToCartUpdatesBadgeCount() {
        openProductsPage();
        int before = getCartBadgeCount();
        addProductToCart(1);
        Assert.assertEquals(getCartBadgeCount(), before + 1);
    }

    @Test
    public void removeFromCartButtonAppearsForItems() {
        addProductToCart(2);
        WebElement removeButton = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("remove-from-cart-2")));
        int before = getCartBadgeCount();
        removeButton.click();
        waitFor(5).until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("#shopping-cart-btn span"), String.valueOf(before - 1)));
        Assert.assertTrue(driver.findElements(By.id("remove-from-cart-2")).isEmpty());
    }

    @Test
    public void addingMultipleQuantityPersistsInCart() {
        addProductToCart(3, 3);
        openCartPage();
        WebElement cartItem = waitFor(5).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("cart-item")));
        Assert.assertTrue(cartItem.getText().contains("x 3"));
    }
}
