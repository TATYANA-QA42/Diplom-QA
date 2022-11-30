package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.FormsPayments;


import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestPay {
    private FormsPayments cardPayment = new FormsPayments();

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
    void shouldPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        System.out.println(cardNumber);
        cardPayment.debitPurchase();

        FormsPayments a = cardPayment.pageFieldInfo(cardNumber);
        System.out.println(a);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, DBHelper.getStatusFromPaymentEntity());

    }

    @Test
    void shouldPayApprovedCardNamedInEng() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardDECLINED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(statusDECLINED, DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutCardNumber() throws SQLException {
        val cardNumber = DataHelper.getCardInfo("", DataHelper.getMoth(), DataHelper.getYear(1),
                DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCard();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutMonth() throws SQLException {
        var cardNumber = DataHelper.getCardInfo(cardAPPROVED, "",
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageMonth();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutYear() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                "", DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageYear();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutOwner() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), "", DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageOwner();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutCVC() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), "");
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedOwnerLength50() {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerLenght(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        assertEquals(DataHelper.getOwnerLenght(), cardPayment.checkLengthOwner());
    }

    @Test
    void shouldPayApprovedOwnerSimbol() { // ошибочный
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), "_________________________________", DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageOwnerSimbol();
    }

    @Test
    void shouldPayApprovedCardNotFull() throws SQLException {
        val cardNumber = DataHelper.getCardInfo("444444444444", DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCard();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedCardYearExpired() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(-1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageYearExpired();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedCardExpiredMonth() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, "01",
                DataHelper.getYear(0), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageExpiredMonth();
        assertNull(DBHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayNotApprovedCardAfterClose() {
        val cardNumber = DataHelper.getCardInfo("4444444444446547", DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.close();
        cardPayment.checkApprovedMessageNotVisible();
    }

    @Test
    void shouldCreditPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, DBHelper.getStatusFromCreditRequestEntity());

    }

    @Test
    void shouldCreditInfoForPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardDECLINED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(statusDECLINED, DBHelper.getStatusFromCreditRequestEntity());
    }

    @Test
    void shouldCreditInfoForPayInvalidCVC() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), "000");
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertNull(DBHelper.getStatusFromCreditRequestEntity());
    }
}