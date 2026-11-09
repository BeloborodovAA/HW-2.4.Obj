package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        loginPage.validLogin(DataHelper.getLogin(), DataHelper.getPassword());
        loginPage.validVerify(DataHelper.getVerificationCode());
    }

    @Test
    void shouldTransferBetweenCards() {
        var dashboardPage = new DashboardPage();

        int balanceFirstBefore = dashboardPage.getCardBalance(DataHelper.getFirstCardId());
        int balanceSecondBefore = dashboardPage.getCardBalance(DataHelper.getSecondCardId());

        int amount = 1000;

        var transferPage = dashboardPage.selectCardToTopUp(DataHelper.getSecondCardId());

        dashboardPage = transferPage.transfer(DataHelper.getFirstCardNumber(), amount);

        int balanceFirstAfter = dashboardPage.getCardBalance(DataHelper.getFirstCardId());
        int balanceSecondAfter = dashboardPage.getCardBalance(DataHelper.getSecondCardId());

        assertEquals(balanceFirstBefore - amount, balanceFirstAfter);
        assertEquals(balanceSecondBefore + amount, balanceSecondAfter);
    }
}