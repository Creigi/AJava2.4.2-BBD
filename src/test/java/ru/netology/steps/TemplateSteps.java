package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;


public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

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
        loginWithNameAndPassword(login, password);
        setValidCode("12345");
        verifyDashboardPage();
    }

    @Когда("пользователь переводит {int} рублей с карты {int} на свою {int} карту с главной страницы")
    public void deposit(int amount, int cardPositionFrom, int cardPositionTo) {
        dashboardPage.depositCard(amount, cardPositionFrom, cardPositionTo);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void getBalance(int cardPosition, int sum) {
        Assertions.assertEquals(sum, dashboardPage.getBalance(cardPosition));
    }
}
