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
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll () {
        SelenideLogger.removeListener("allure");
    }
@Test
    @DisplayName ("1) HappyPath PayCard Status APPROVED")
    void validPayCard () {
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
        tourPage.completePayFrom(DataHelper.getDeclinedNumber(),DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
    tourPage.continueClick();
    Assertions.assertAll(
            () -> tourPage.denialAssertion(),
            () -> tourPage.payDeclinedStatusAssertion(),
            () -> tourPage.payAcceptCountAssertion(),
            () -> tourPage.orderAcceptCountAssertion() );
}
@Test@DisplayName("5) UnHappyPath PayCard Random Invalid CardField")
    void failPayInvalidCard () {
        tourPage.completePayFrom(DataHelper.getRandomNumber(),DataHelper.getMonth(0), DataHelper.getYear(0), DataHelper.getEngHolder(), DataHelper.getCVC());
    tourPage.continueClick();
    Assertions.assertAll(
            () -> tourPage.denialAssertion(),
            () -> tourPage.payDenialCountAssertion(),
            () -> tourPage.orderDenialCountAssertion()
    );
}
}
