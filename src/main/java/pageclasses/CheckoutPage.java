package pageclasses;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import managers.DriverManager;
public class CheckoutPage {
	

    public void enterCustomerInformation(
            String firstName,
            String lastName,
            String postalCode) {

        WebElement firstNameField =
                DriverManager.getDriver().findElement(By.id("first-name"));

        firstNameField.sendKeys(firstName);

        WebElement lastNameField =
                DriverManager.getDriver().findElement(
                        with(By.tagName("input"))
                                .below(By.id("first-name"))
                );

        lastNameField.sendKeys(lastName);

        DriverManager.getDriver().findElement(By.id("postal-code"))
              .sendKeys(postalCode);
    }
}
