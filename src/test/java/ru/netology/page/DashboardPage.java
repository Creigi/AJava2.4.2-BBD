package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection depositButton = $$("[data-test-id=action-deposit]");
    private SelenideElement reloadButton = $("[data-test-id=action-reload]");
    private ElementsCollection cardsText = $$(".list__item");

    public void verifyIsDashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage selectCardToDeposit(DataHelper.CardInfo cardInfo) {
        depositButton.get(cardInfo.getCardPosition() - 1).click();
        return new TransferPage();
    }

    public int getBalance(DataHelper.CardInfo cardInfo) {
        return Integer.parseInt(cardsText.get(cardInfo.getCardPosition() - 1).text().split(" ")[5]);
    }
}