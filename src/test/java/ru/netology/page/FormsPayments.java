package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FormsPayments {

    private SelenideElement inputCardNumber = $("input[type=\"text\"][placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement inputMonth = $("input[type=\"text\"][placeholder=\"08\"]");
    private SelenideElement inputYear = $("input[type=\"text\"][placeholder=\"22\"]");
    private SelenideElement inputOwner = $$(".input").find(exactText("Владелец")).$(".input__control");
    private SelenideElement inputCVC = $("input[type=\"text\"][placeholder=\"999\"]");
    private SelenideElement buyContinue = $$(".button").find(exactText("Продолжить"));

    private SelenideElement checkApprovedMessage = $$(".notification__title").find(exactText("Успешно"));
    private SelenideElement checkDeclinedMessage = $$(".notification__title").find(exactText("Ошибка"));
    private SelenideElement checkDeclinedMessageClose = $$(".notification__closer").last();

    private SelenideElement checkErrorMessageCard = $$(".input__top").find(exactText("Номер карты")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageMonth = $$(".input__top").find(exactText("Месяц")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageExpiredMonth = $$(".input__top").find(exactText("Месяц")).parent().
            $$(".input__sub").find(exactText("Неверно указан срок действия карты"));
    private SelenideElement checkErrorMessageYear = $$(".input__top").find(exactText("Год")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageYearExpired = $$(".input__top").find(exactText("Год")).parent().
            $$(".input__sub").find(exactText("Истёк срок действия карты"));
    private SelenideElement checkErrorMessageOwner = $$(".input__top").find(exactText("Владелец")).parent().
            $$(".input__sub").find(exactText("Поле обязательно для заполнения"));
    private SelenideElement checkErrorMessageOwnerSimbol = $$(".input__top").find(exactText("Владелец")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageCVC = $$(".input__top").find(exactText("CVC/CVV")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));


    public FormsPayments pageFieldInfo(DataHelper.CardInfo info) {
        inputCardNumber.setValue(info.getCardNumber());
        inputMonth.setValue(info.getMonth());
        inputYear.setValue(info.getYear());
        inputOwner.setValue(info.getOwner());
        inputCVC.setValue(info.getCvc());
        buyContinue.click();
        return new FormsPayments();
    }

    public void close() {
        checkDeclinedMessageClose.waitUntil(visible, 20000).click();
    }

    public void checkApprovedMessage() {
        checkApprovedMessage.waitUntil(visible, 20000);
    }

    public void checkApprovedMessageNotVisible() {
        checkApprovedMessage.shouldBe(disappear);
    }

    public void checkDeclinedMessage() {
        checkDeclinedMessage.waitUntil(visible, 20000);
    }

    public void checkErrorMessageCard() {
        checkErrorMessageCard.shouldBe(visible);
    }

    public void checkErrorMessageMonth() {
        checkErrorMessageMonth.shouldBe(visible);
    }

    public void checkErrorMessageYear() {
        checkErrorMessageYear.shouldBe(visible);
    }

    public void checkErrorMessageYearExpired() {
        checkErrorMessageYearExpired.shouldBe(visible);
    }

    public void checkErrorMessageExpiredMonth() {
        checkErrorMessageExpiredMonth.shouldBe(visible);
    }

    public void checkErrorMessageOwner() {
        checkErrorMessageOwner.shouldBe(visible);
    }

    public void checkErrorMessageOwnerSimbol() {
        checkErrorMessageOwnerSimbol.shouldBe(visible);
    }

    public void checkErrorMessageCVC() {
        checkErrorMessageCVC.shouldBe(visible);
    }

    public String checkLengthOwner() {
        return String.valueOf(inputOwner.getValue());
    }
}