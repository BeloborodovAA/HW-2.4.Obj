package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(DataHelper.getLogin(), DataHelper.getPassword());
        verificationPage.validVerify(DataHelper.getVerificationCode());
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

    @Test
    void shouldNotTransferMoreThanBalance() {
        var dashboardPage = new DashboardPage();
        int balanceFirstBefore = dashboardPage.getCardBalance(DataHelper.getFirstCardId());
        int amount = balanceFirstBefore + 1000;
        var transferPage = dashboardPage.selectCardToTopUp(DataHelper.getSecondCardId());
        
        transferPage.transfer(DataHelper.getFirstCardNumber(), amount);
        transferPage.verifyErrorIsVisible();
        
        open("http://localhost:9999/dashboard");
        dashboardPage = new DashboardPage();
        
        int balanceFirstAfter = dashboardPage.getCardBalance(DataHelper.getFirstCardId());
        assertEquals(balanceFirstBefore, balanceFirstAfter, "Баланс не должен меняться при переводе суммы больше остатка");
    }
}
