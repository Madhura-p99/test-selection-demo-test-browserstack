package com.browserstack;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductAddToCartBadgeTest extends BaseUiTest {

    @Test
    public void addToCartUpdatesBadgeCount() {
        openProductsPage();
        int before = getCartBadgeCount();
        addProductToCart(1);
        Assert.assertEquals(getCartBadgeCount(), before + 1);
    }
}
