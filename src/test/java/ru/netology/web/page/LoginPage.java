package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[name='login']");
    private final SelenideElement passwordField = $("[name='password']");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");

    public VerificationPage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }
}
