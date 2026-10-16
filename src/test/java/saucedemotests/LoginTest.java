package saucedemotests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageclasses.CheckoutPage;
import pageclasses.InventoryPage;
import pageclasses.LoginPage;

public class LoginTest extends BaseTest {

	@Test
	public void loginSuccesTest() {
		LoginPage loginObj = new LoginPage();
		loginObj.enterUsername("standard_user");
		loginObj.enterPassword("secret_sauce");
		loginObj.clickonSubmit();

		System.out.println("logged in");

		InventoryPage inventoryPage = new InventoryPage();

		Map<String, Double> filteredProducts = inventoryPage.getFilteredProducts();
		System.out.print(filteredProducts.size());

		System.out.println("Products >= $15:");
		filteredProducts.forEach((name, price) -> System.out.println(name + " -> $" + price));

		Assert.assertFalse(filteredProducts.isEmpty(), "No products found >= $15");

		/*
		 * Validate Stream filter
		 */
		Assert.assertTrue(filteredProducts.values().stream().allMatch(price -> price >= 15.00));

		inventoryPage.hoverAndScrollProducts();

		/*
		 * Queue
		 */
		inventoryPage.loadCheckoutQueue();
		
		while (!inventoryPage.isQueueEmpty()) {

			String product = inventoryPage.getNextCheckoutProduct();

			System.out.println("Checkout Queue -> " + product);
			inventoryPage.addProductsToCartSequentially();

			
		}
		
		inventoryPage.clickCart();
		
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.scrollToBottom();
		checkoutPage.clickCheckout();
		checkoutPage.enterCustomerInformation("Manpreet", "K", "L6X 0L8");
		checkoutPage.clickContinue();
		//String twitterTitle = inventoryPage.verifyTwitterTab();

		//System.out.println("Twitter Title: " + twitterTitle);
		

		//String linkedTitle = inventoryPage.verifyLinkedinTab();

		//System.out.println("Linked Title: " + linkedTitle);
		
		

	}

}
