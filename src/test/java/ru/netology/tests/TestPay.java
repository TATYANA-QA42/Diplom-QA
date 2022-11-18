package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.FormsPayments;
import ru.netology.page.HomePage;

import javax.xml.crypto.Data;

import static com.codeborne.selenide.Selenide.open;

public class TestPay {
    FormsPayments tourPage = new FormsPayments();
    HomePage homePage = new HomePage();

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterEach
    void clearDB() {
        DBHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("1) HappyPath PayCard Status APPROVED")
    void validPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("3) HappyPath PayCard Status DECLINED")
    void validPayCardDeclined() {
        tourPage.completePayFrom(DataHelper.getDeclinedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.payDeclinedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion());
    }

    @Test
    @DisplayName("5) UnHappyPath PayCard Random Invalid CardField")
    void failPayInvalidCard() {
        tourPage.completePayFrom(DataHelper.getRandomNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );

    }

    @Test
    @DisplayName("7) PayCard Positive Test CardField 16symbol")
    void validPayCardSixteenSymbols() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("9) PayCard Negative Test CardField Empty")
    void invalidPayCardFieldEmpty() {
        tourPage.completePayFrom(DataHelper.getEmptyNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.numberFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("10) PayCard Negative Test CardField One Symbol")
    void invalidPayCardFieldOneSymbol() {
        tourPage.completePayFrom(DataHelper.getOneRandomNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.numberFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("15) PayCard Positive Test MonthField 12 month")
    void validTwelveMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getTwelveMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("16) PayCard Positive Test MonthField 11 month")
    void validElevenMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getElevenMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("17) PayCard Positive Test MonthField 01 month")
    void validFirstMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getFirstMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("18) PayCard Positive Test MonthField 02 month")
    void validSecondMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getSecondMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("19) PayCard Negative Test MonthField 13 month")
    void shouldFailureBuyByInvalidThirteenMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getSecondMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.monthFieldPeriodError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("20) PayCard Negative Test MonthField 00 month")
    void shouldFailureBuyByInvalidZeroMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getSecondMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.monthFieldPeriodError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("21) PayCard Negative Test MonthField Empty month")
    void shouldFailureBuyByInvalidEmptyMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getSecondMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.monthFieldPeriodError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("22) PayCard Negative Test MonthField One Symbol month")
    void shouldFailureBuyByInvalidOneSymbolMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getSecondMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.monthFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }


    @Test
    @DisplayName("31) PayCard Positive Test YearField Current Year")
    void shouldSuccessfulBuyByValidCurrentYearYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("32) PayCard Positive Test YearField Current Year Plus One")
    void shouldSuccessfulBuyByValidCurrentYearPlusOneYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("33) PayCard Positive Test YearField Current Year Plus Five")
    void shouldSuccessfulBuyByValidCurrentYearPlusFiveYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(5), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("34) PayCard Positive Test YearField Current Year Plus Five")
    void shouldSuccessfulBuyByValidCurrentYearPlusFourYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(4), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("35) PayCard Positive Test YearField Current Year Minus One")
    void shouldFailureBuyByValidCurrentYearMinusOneYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(-1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.yearFieldMinusPeriodError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("36) PayCard Positive Test YearField Current Year Plus Six")
    void shouldFailureBuyByValidCurrentYearPlusSixYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(6), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.yearFieldPlusPeriodError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("37) PayCard Positive Test YearField Current Year Empty")
    void shouldFailureBuyByInvalidYearEmptyYearFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getEmptyYear(), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.yearFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }
    @Test
    @DisplayName("38) PayCard Positive Test YearField Current Year Empty")
    void failInvalidYearOneSymbolPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getOneSymbolYear(), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.yearFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }
    @Test
    @DisplayName("47) PayCard Positive Test HolderField Three Symbols")
    void successfulValidHolderThreeSymbol() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }
    @Test
    @DisplayName("48) PayCard Positive Test HolderField Four Symbol")
    void validHolderFieldFourSymbolsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }
    @Test
    @DisplayName("49) PayCard Positive Test HolderField Nineteen Symbols")
    void validHolderFieldNineteenSymbols(){
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("???????????????????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }
}