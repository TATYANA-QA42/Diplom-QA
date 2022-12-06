package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.FormsPayments;
import ru.netology.page.PageButtons;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Credit {
    private FormsPayments cardPayment = new FormsPayments();
    private PageButtons button = new PageButtons();
    private final static String statusAPPROVED = "APPROVED";
    private final static String statusDECLINED = "DECLINED";
    private final static String cardAPPROVED = "4444444444444441";
    private final static String cardDECLINED = "4444444444444442";

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:8080", FormsPayments.class);
    }

    @AfterEach
    void clearAll() throws SQLException {
        DBHelper.clearDBTables();
    }

    @Test
    void shouldCreditPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        button.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, DBHelper.getStatusFromCreditRequestEntity());

    }

    @Test
    void shouldCreditInfoForPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardDECLINED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        button.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(statusDECLINED, DBHelper.getStatusFromCreditRequestEntity());
    }

    @Test
    void shouldCreditInfoForPayInvalidCVC() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), "000");
        button.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertNull(DBHelper.getStatusFromCreditRequestEntity());
    }
}

