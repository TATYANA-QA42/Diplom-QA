package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class PageButtons {
    private SelenideElement buyTour = $$(".button").find(exactText("Купить"));
    private SelenideElement buyTourInCredit = $$(".button").find(exactText("Купить в кредит"));

    public void debitPurchase() {
        buyTour.click();
    }

    public void creditPurchase() {
        buyTourInCredit.click();
    }
}
