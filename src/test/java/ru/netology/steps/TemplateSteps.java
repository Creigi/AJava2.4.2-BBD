package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;


public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.verifyIsDashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void verifyCodeIsInvalid() {
        verificationPage.verifyCodeIsInvalid();
    }

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginSuccess(String login, String password) {
        openAuthPage("http://localhost:9999");
        verificationPage = loginPage.validLogin(login, password);
        dashboardPage = verificationPage.validVerify("12345");
    }

    @Когда("пользователь переводит {int} рублей с карты {string} на свою {int} карту с главной страницы")
    public void deposit(int amount, String cardNumber, int cardPositionTo) {
        var firstCardInfo = new DataHelper.CardInfo(cardPositionTo, DataHelper.getFirstCardInfo().getCardNumber());
        var secondCardInfo = new DataHelper.CardInfo(DataHelper.getSecondCardInfo().getCardPosition(), cardNumber);
        transferPage = dashboardPage.selectCardToDeposit(firstCardInfo);
        dashboardPage = transferPage.makeValidTransfer(amount, secondCardInfo);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void getBalance(int cardPosition, int sum) {
        var firstCardInfo = new DataHelper.CardInfo(cardPosition, DataHelper.getFirstCardInfo().getCardNumber());
        Assertions.assertEquals(sum, dashboardPage.getBalance(firstCardInfo));
    }
}
