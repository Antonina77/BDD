package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;
import static ru.netology.data.DataHelper.getFirstCard;
import static ru.netology.data.DataHelper.getSecondCard;


public class TransferPage {

    private  SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void transferMoneyFromFirstCard(int amount) {
        amountField.setValue(valueOf(amount));
        fromField.setValue(valueOf(getFirstCard()));
        transferButton.click();
        new DashboardPage();
    }

    public void transferMoneyFromSecondCard(int amount) {
        amountField.setValue(valueOf(amount));
        fromField.setValue(valueOf(getSecondCard()));
        transferButton.click();
        new DashboardPage();
    }

    public void getError() {
        $(byText("Недостаточно средств для перевода!")).shouldBe(visible);
    }
}
