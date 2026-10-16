package pageclasses;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import managers.DriverManager;
import utils.UIActionsUtils;
import utils.WaitUtils;
public class CheckoutPage {
	
	private By btnCheckout = By.cssSelector("[data-test='checkout']");
	By continueButton = By.cssSelector("#continue");
	By finishButton = By.cssSelector("#finish");
	By firstNameVal = By.cssSelector("#first-name");
	WebElement checkoutEle ;
    public void enterCustomerInformation(
            String firstName,
            String lastName,
            String postalCode) {
    	//WaitUtils.visibilityOfElementLocated(firstNameVal);

        WebElement firstNameField =
                DriverManager.getDriver().findElement(firstNameVal);

        firstNameField.sendKeys(firstName);

        WebElement lastNameField =
                DriverManager.getDriver().findElement(
                        with(By.tagName("input"))
                                .below(By.cssSelector("#first-name"))
                );

        lastNameField.sendKeys(lastName);

        DriverManager.getDriver().findElement(By.id("postal-code"))
              .sendKeys(postalCode);
        
    }
    public void scrollToBottom()
    {
    	WaitUtils.visibilityOfElementLocated(btnCheckout);
    	checkoutEle=  DriverManager.getDriver().findElement(By.id("checkout"));
    	WaitUtils.actions().scrollToElement(checkoutEle);
    }
    public void clickCheckout()
    {
    	UIActionsUtils.click(btnCheckout, "Checkout");
    }
    public void clickContinue()
    {
    	UIActionsUtils.click(continueButton, "continue");
    	UIActionsUtils.click(finishButton,"finish");
    }
    
}
