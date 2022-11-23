package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.*;
import ru.netology.data.APIHelper;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.FormsPayments;

public class APITourTest {
    FormsPayments tourPage = new FormsPayments();

    @AfterEach
    void clearDB() {
        DBHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.removeListener("allure");

    }

    @Test
    @DisplayName("87) HappyPath PayCard Status Approved API Test Response 200")
    void ResponseTwoHundredByPayPayCardApproved() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyPayCardAPI(DataHelper.getValidApprovedCard())),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );

    }

    @Test
    @DisplayName("88) HappyPath PayCard Status Declined API Test Response 200")
    void ResponseTwoHundredByPayPayCardDeclined() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyPayCardAPI(DataHelper.getValidDeclinedCard())),
                () -> tourPage.payDeclinedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("89) HappyPath CreditCard Status Approved API Test Response 200")
    void ResponseTwoHundredByPayCreditCardApproved() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyPayCardAPI(DataHelper.getValidApprovedCard())),
                () -> tourPage.creditApprovedStatusAssertion(),
                () -> tourPage.creditAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("90) HappyPath CreditCard Status Declined API Test Response 200")
    void ResponseTwoHundredByPayCreditCardDeclined() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, APIHelper.buyPayCardAPI(DataHelper.getValidApprovedCard())),
                () -> tourPage.creditDeclinedStatusAssertion(),
                () -> tourPage.creditAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("91) Empty PayCard API Test Response 400")
    void ResponseFourHundredByPayEmptyPayCard() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getEmptyCard())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()

        );
    }

    @Test
    @DisplayName("92) Approved PayCard And Random Invalid Other Field API Test Response 400")
    void ResponseFourHundredByApprovedPayCardAndRandomInvalidOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getValidApprovedCardAndRandomInvalidOtherField())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()

        );
    }

    @Test
    @DisplayName("93) Approved PayCard And Empty Other Field API Test Response 400")
    void ResponseFourHundredByApprovedPayCardAndEmptyOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyPayCardAPI(DataHelper.getRandomCardNumberAndValidOtherField())),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()

        );
    }

    @Test
    @DisplayName("95) Empty CreditCard API Test Response 400")
    void ResponseFourHundredByPayEmptyCreditCard() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getEmptyCard())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("96) Approved CreditCard And Random Invalid Other Field API Test Response 400")
    void ResponseFourHundredByPayApprovedCreditCardAndRandomInvalidOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getValidApprovedCardAndRandomInvalidOtherField())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("96) Approved CreditCard And Empty Other Field API Test Response 400")
    void ResponseFourHundredByApprovedCreditCardAndEmptyOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getValidApprovedCardAndEmptyOtherField())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("96) Approved CreditCard And Empty Other Field API Test Response 400")
    void ResponseFourHundredByRandomCreditCardAndEmptyOtherField() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(400, APIHelper.buyCreditCardAPI(DataHelper.getRandomCardNumberAndValidOtherField())),
                () -> tourPage.creditDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }
}
