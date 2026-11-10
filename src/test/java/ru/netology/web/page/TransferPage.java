package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TransferPage {
    private final ElementsCollection inputs = $$(".input__control");
    private final SelenideElement amountInput = inputs.get(0);
    private final SelenideElement fromInput = inputs.get(1);
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public DashboardPage transfer(String fromCardNumber, int amount) {
        fromInput.setValue(fromCardNumber);
        amountInput.setValue(String.valueOf(amount));
        transferButton.click();

        return new DashboardPage();
    }
    
    public void verifyErrorIsVisible() {
        errorMessage.shouldBe(visible);
    }
}
