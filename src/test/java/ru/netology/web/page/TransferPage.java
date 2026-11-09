package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountInput = $("[placeholder='0000 0000 0000 0000']");

    private final SelenideElement fromInput = $("[data-test-id='from'] input");

    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public DashboardPage transfer(String fromCardNumber, int amount) {
        if (fromInput.exists()) {
            fromInput.setValue(fromCardNumber);
        }

        amountInput.setValue(String.valueOf(amount));

        transferButton.click();

        return new DashboardPage();
    }
}