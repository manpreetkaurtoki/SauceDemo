package pageclasses;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import managers.DriverManager;
import utils.UIActionsUtils;
import utils.WaitUtils;

public class InventoryPage {

	private By inventoryItems = By.cssSelector(".inventory_item");
	private By itemName = By.cssSelector(".inventory_item_name");
	private By itemPrice = By.cssSelector(".inventory_item_price");

	private By twitter = By.cssSelector("a[data-test='social-x']");
	private By linkedin = By.cssSelector("a[data-test='social-linkedin']");

	private final ConcurrentHashMap<String, Double> inventoryMap = new ConcurrentHashMap<>();

	private final Deque<String> checkoutQueue = new ArrayDeque<>();

	/*
	 * Extract all products map() filter() sorted()
	 */
	public Map<String, Double> getFilteredProducts() {
//WaitUtils.visibilityOfAllElementsLocatedBy(inventoryItems);
		//DriverManager.getDriver().findElements(inventoryItems)
		List<WebElement> products = WaitUtils.visibilityOfAllElementsLocatedBy(inventoryItems);

		Map<String, Double> filteredProducts = products.stream()

				// Extract title + price
				.map(product -> {

					String name = product.findElement(itemName).getText();

					String priceText = product.findElement(itemPrice).getText().replace("$", "");

					double price = Double.parseDouble(priceText);
					
					

					return new AbstractMap.SimpleEntry<>(name, price);
				})

				// Keep products >= $15
				.filter(product -> product.getValue() >= 15.00)

				// Highest price first
				.sorted(Map.Entry.<String, Double>comparingByValue().reversed())

				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));

		inventoryMap.putAll(filteredProducts);

		return filteredProducts;
	}

	public ConcurrentHashMap<String, Double> getInventoryMap() {
		return inventoryMap;
	}

	public void loadCheckoutQueue() {

		checkoutQueue.clear();

		getFilteredProducts().keySet().forEach(checkoutQueue::offerLast);
	}

	public String getNextCheckoutProduct() {
		return checkoutQueue.pollFirst();
	}

	public boolean isQueueEmpty() {
		return checkoutQueue.isEmpty();
	}

	public void hoverAndScrollProducts() {

		Actions actions = new Actions(DriverManager.getDriver());

		List<WebElement> products = DriverManager.getDriver().findElements(inventoryItems);

		products.forEach(product -> {

			actions.scrollToElement(product).moveToElement(product).perform();
		});
	}
	
	public void addProductsToCartSequentially() {

	    String productName;

	    while ((productName = checkoutQueue.pollFirst()) != null) {

	        System.out.println(
	                "Adding product to cart: " + productName
	        );

	        /*
	         * Find the inventory card containing
	         * this particular product name.
	         */
	        By productCard = By.xpath(
	        	    "//div[contains(@class,'inventory_item')]" +
	        	    "[.//*[normalize-space()='" + productName + "']]"
	        	);

	        WebElement card =
	                DriverManager.getDriver()
	                        .findElement(productCard);
	        
	        /*
	         * Find Add to cart button ONLY
	         * inside this product card.
	         */
	        WebElement addToCartButton =
	                card.findElement(By.tagName("button"));

	        addToCartButton.click();

	        System.out.println(
	                productName + " added successfully."
	        );
	    }
	}

	public String verifyTwitterTab() {

		return UIActionsUtils.openSocial(twitter, DriverManager.getDriver().findElement(twitter).getAttribute("href"));
	}
	public String verifyLinkedinTab() {

		return UIActionsUtils.openSocial(linkedin, DriverManager.getDriver().findElement(linkedin).getAttribute("href"));
	}

}
