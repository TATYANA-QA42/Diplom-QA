package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.FormsPayments;
import ru.netology.page.HomePage;

import java.util.ArrayList;

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
    // Поле номер карты
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
    // Поле месяц
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
    void invalidThirteenMonthMonthFieldPayCard() {
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
    void iinvalidZeroMonthMonthFieldPayCard() {
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
    void invalidEmptyMonthMonthFieldPayCard() {
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
    void invalidOneSymbolMonthMonthFieldPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getSecondMonth(), DataHelper.getYear(1), DataHelper.getEngHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.monthFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    //Поле год
    @Test
    @DisplayName("31) PayCard Positive Test YearField Current Year")
    void svalidCurrentYearYearFieldPayCard() {
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
    void validCurrentYearPlusOneYearFieldPayCard() {
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
    void validCurrentYearPlusFiveYearFieldPayCard() {
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
    void validCurrentYearPlusFourYearFieldPayCard() {
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
    void validCurrentYearMinusOneYearFieldPayCard() {
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
    void validCurrentYearPlusSixYearFieldPayCard() {
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
    void invalidYearEmptyYearFieldPayCard() {
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
    //Поле владелец
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
    void validHolderFieldNineteenSymbols() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("???????????????????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("50) PayCard Positive Test HolderField Twenty Symbols")
    void validHolderFieldTwentySymbols() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("??????????????????????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("51) PayCard Positive Test HolderField With Spacebar")
    void validHolderFieldWithSpaceBarPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("??????? ???????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("52) PayCard Positive Test HolderField With Dash")
    void validHolderFieldWithDashPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("???????-???????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.acceptAssertion(),
                () -> tourPage.payApprovedStatusAssertion(),
                () -> tourPage.payAcceptCountAssertion(),
                () -> tourPage.orderAcceptCountAssertion()
        );
    }

    @Test
    @DisplayName("53) PayCard Negative Test HolderField TwentyOne Symbols")
    void invalidHolderFieldTwentyOneSymbolsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("?????????????????????"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("544) PayCard Negative Test HolderField Two Symbols")
    void invalidHolderFieldTwoSymbolsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("??"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("54) PayCard Negative Test HolderField Empty")
    void invalidHolderFieldEmptyPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEmptyHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldEmptyError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("55) PayCard Negative Test Special Characters")
    void invalidHolderFieldSpecialCharactersPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getSpecialCharactersHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldEmptyError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("56) PayCard Negative Test HolderField Spacebars")
    void invalidHolderFieldTestSpacebarsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getSpacesHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldEmptyError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("57) PayCard Negative Test HolderField Numerify")
    void invalidHolderFieldNumerifyPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getNumerifyHolder("#########"), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldEmptyError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("58) PayCard Negative Test HolderField RU")
    void invalidHolderFieldRUPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getRuHolder(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("599) PayCard Negative Test HolderField Start And Finish Spacebars")
    void invalidHolderFieldStartAndFinishSpacebarsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getChoiceSymbolHolder("  ??????   ????  "), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("59) PayCard Negative Test HolderField With Lower Case")
    void invalidHolderFieldWithLowerCasePayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolderWithLowerCase(), DataHelper.getCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.holderFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }
    //Поле CVC

    @Test
    @DisplayName("75) PayCard Positive Test CVCField Three Symbols")
    void validCVCFieldThreeSymbolsPayCard() {
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
    @DisplayName("76) PayCard Negative Test CVCField Two Symbols")
    void invalidCVCFieldTwoSymbolsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getTwoSymbolsCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.CVCFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("77) PayCard Negative Test CVCField One Symbols")
    void invalidCVCFieldOneSymbolsPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getOneSymbolCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.CVCFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("78) PayCard Negative Test CVCField Empty")
    void invalidCVCFieldEmptyPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getEmptyCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.CVCFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }

    @Test
    @DisplayName("79) PayCard Negative Test CVCField Zero")
    void invalidCVCFieldZeroPayCard() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getZeroSymbolsCVC());
        tourPage.continueClick();
        Assertions.assertAll(
                () -> tourPage.denialAssertion(),
                () -> tourPage.CVCFieldFormatError(),
                () -> tourPage.payDenialCountAssertion(),
                () -> tourPage.orderDenialCountAssertion()
        );
    }
    //Остальные пути

    @Test
    @DisplayName("85) PayCard Change On CreditCard Stay Completed")
    void successfulChangeOnCreditCardStayCompleted() {
        tourPage.completePayFrom(DataHelper.getApprovedNumber(), DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
        ArrayList<String> beforeClick = tourPage.getFrom();
        homePage.clickCreditButton();
        ArrayList<String> afterClick = tourPage.getFrom();
        Assertions.assertAll(
                () -> Assertions.assertEquals(beforeClick.get(0), afterClick.get(0)),
                () -> Assertions.assertEquals(beforeClick.get(1), afterClick.get(1)),
                () -> Assertions.assertEquals(beforeClick.get(2), afterClick.get(2)),
                () -> Assertions.assertEquals(beforeClick.get(3), afterClick.get(3)),
                () -> Assertions.assertEquals(beforeClick.get(4), afterClick.get(4))
        );
    }

}
