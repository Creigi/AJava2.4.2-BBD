package ru.netology.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
// Данный класс как пример генерации тестовых данных
// Вместа передачи данных через сценарий (feature)
// можно вызывать подобные методы непосредственно в шагах сценария (steps)
public class DataHelper {

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static String cardsNumber(int cardPosition) {
        String[] cardNumber = new String[2];
        cardNumber[0] = "5559 0000 0000 0001";
        cardNumber[1] = "5559 0000 0000 0002";
        return cardNumber[cardPosition];
    }
}