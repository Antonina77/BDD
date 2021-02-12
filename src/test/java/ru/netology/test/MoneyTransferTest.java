package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.page.DashboardPage.pushFirstCardButton;
import static ru.netology.page.DashboardPage.pushSecondCardButton;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyFromFirstCardToSecondCard() {
        val dashboardPage = new DashboardPage();
        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        int amount = 1500;
        val transferPage = pushSecondCardButton();
        transferPage.transferMoneyFromFirstCard(amount);
        val firstCardBalanceResult = firstCardBalanceStart - amount;
        val secondCardBalanceResult = secondCardBalanceStart + amount;
        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    public void shouldTransferAllMoneyFromFirstCardToSecondCard() {
        val dashboardPage = new DashboardPage();
        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        int amount = 10000;
        val transferPage = pushSecondCardButton();
        transferPage.transferMoneyFromFirstCard(amount);
        val firstCardBalanceResult = firstCardBalanceStart - amount;
        val secondCardBalanceResult = secondCardBalanceStart + amount;
        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    public void shouldTransferMoneyFromSecondCardToFirstCard() {
        val dashboardPage = new DashboardPage();
        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        int amount = 1000;
        val transferPage = pushFirstCardButton();
        transferPage.transferMoneyFromSecondCard(amount);
        val firstCardBalanceResult = firstCardBalanceStart + amount;
        val secondCardBalanceResult = secondCardBalanceStart - amount;
        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    public void shouldTransferAllMoneyFromSecondCardToFirstCard() {
        val dashboardPage = new DashboardPage();
        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        int amount = 10000;
        val transferPage = pushFirstCardButton();
        transferPage.transferMoneyFromSecondCard(amount);
        val firstCardBalanceResult = firstCardBalanceStart + amount;
        val secondCardBalanceResult = secondCardBalanceStart - amount;
        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    public void shouldNoTransferMoneyIfBalanceLessTransfer() {
        int amount = 55_000;
        val transactionPage = pushSecondCardButton();
        transferPage.transferMoneyFromFirstCard(amount);
        transferPage.getError();
    }
}

