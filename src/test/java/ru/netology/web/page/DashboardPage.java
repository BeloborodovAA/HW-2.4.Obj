package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final ElementsCollection cards = $$("ul.list .list__item div[data-test-id]");

    public int getCardBalance(String cardId) {
        cards.first().shouldBe(visible);

        SelenideElement card = cards.findBy(text(cardId));
        return extractBalance(card.text());
    }

    public TransferPage selectCardToTopUp(String cardId) {
        SelenideElement card = cards.findBy(text(cardId));
        card.$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";

        int start = text.indexOf(balanceStart);
        String subString = text.substring(start + balanceStart.length());
        int finish = subString.indexOf(balanceFinish);
        String value = subString.substring(0, finish).trim();

        value = value.replaceAll("\\s+", "");

        return Integer.parseInt(value);
    }
}