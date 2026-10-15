package pageclasses;

import org.openqa.selenium.By;

import utils.UIActionsUtils;

public class LoginPage {

	By usernameInput = By.id("user-name");
	By passwordInput = By.id("password");
	By loginBtn = By.id("login-button");

	public void enterUsername(String username) {
		UIActionsUtils.enterText(usernameInput, username);
	}
	public void enterPassword(String password) {
		UIActionsUtils.enterPassword(passwordInput, password);

	}
	public void clickonSubmit() {
		UIActionsUtils.click(loginBtn,"Login button");

		
	
	}

}
