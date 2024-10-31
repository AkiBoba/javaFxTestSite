package org.example.testsitesel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.example.testsitesel.domain.SeleniumTestResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HelloController {
    @FXML
    private Label welcomeText;
    private Long testUserId = 21499L;
    private String testUserLogin = "info@sparox.ru";
    private String testUserPassword = "GHyuas&!SDjh$#";
    private String chromedriver = "chromedriver.exe";

    private static String done = "Успешно";
    private static String result = "Проведено тестирование открытия страницы ";
    private static String error = "Ошибка тестирования страницы ";

    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        String testUrl;

        List<SeleniumTestResult> results = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", chromedriver);

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        testUrl = "https://sparox.ru/";
        checkUrl(testUrl, results, driver);
        try {
            WebElement enter = driver.findElement(By.xpath("//button[@data-target='#loginModal']"));
            enter.click();
            WebElement inputLogin = driver.findElement(By.id("logemail"));
            inputLogin.sendKeys(testUserLogin);
            WebElement inputPassword = driver.findElement(By.id("logpassword"));
            inputPassword.sendKeys(testUserPassword);
            WebElement login = driver.findElement(By.xpath("//button[@data-action='login']"));
            login.click();
            driver.get("https://sparox.ru/");
            WebElement user = driver.findElement(By.xpath("//div[@class='stickypanel-btn-profile']"));
            String userLogin = user.getText();
            if (userLogin.contains(testUserLogin)) {
                results.add(SeleniumTestResult.builder()
                        .test("Вход по логину и паролю")
                        .result(done)
                        .build()
                );
            }
        } catch (Exception e) {
            results.add(SeleniumTestResult.builder()
                    .test(error + testUrl)
                    .result(e.getClass().getName())
                    .build());
        }

        try {
            WebElement addToCart = driver.findElement(By.xpath("//button[@action='/addtocart']"));
            addToCart.click();
            WebElement cart = driver.findElement(By.className("stickypanel-btn"));
            cart.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            results.add(SeleniumTestResult.builder()
                    .test(error + testUrl)
                    .result(e.getClass().getName())
                    .build());
        }
        checkUrl("https://sparox.ru/catalog/kamaz/amortizator?instock=0&viewtype=list/&sort=1", results, driver);
        try {
            WebElement checkBox = driver.findElement(By.id("checkbox_instock"));
            List<WebElement> goods = driver.findElements(By.xpath("//div[@class='ibox-content hidden-xs hidden-sm p-xs']"));
            int goodsSize = goods.size();
            checkBox.click();
            Thread.sleep(2000);
            goods = driver.findElements(By.xpath("//div[@class='ibox-content hidden-xs hidden-sm p-xs']"));
            if (goods.size() - goodsSize < 0) {
                results.add(SeleniumTestResult.builder()
                        .test("Чек бокс в наличии")
                        .result("Сработал")
                        .build()
                );
            } else {
                results.add(SeleniumTestResult.builder()
                        .test("Чек бокс в наличии")
                        .result("Не сработал")
                        .build()
                );
            }
        } catch (Exception e) {
            results.add(SeleniumTestResult.builder()
                    .test(result + testUrl)
                    .result(done)
                    .build());
        }
        checkUrl("https://sparox.ru/shop", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/delivery", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/about", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/producers", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/vacancies", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/actions", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/howtoorder", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/return", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/feedback", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/help", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/help/abbreviations", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/help/articles", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/checkstatus", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/partner", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/partner/request", results, driver);
        Thread.sleep(1000);
        checkUrl("https://sparox.ru/contacts", results, driver);
        Thread.sleep(1000);
        driver.close();

        StringBuilder output = new StringBuilder();

        // Проходим по списку результатов
        for (SeleniumTestResult result : results) {
            // Форматируем строку для вывода
            output.append(result.getTest()).append(" | ").append(result.getResult()).append("\n");
        }

        // Устанавливаем текст в Label
        welcomeText.setText(output.toString());
    }

    private void checkUrl(String testUrl, List<SeleniumTestResult> results, WebDriver driver) {
        try {
            driver.get(testUrl);
            results.add(SeleniumTestResult.builder()
                    .test(result + testUrl)
                    .result(done)
                    .build());

        } catch (Exception e) {
            results.add(SeleniumTestResult.builder()
                    .test(error + testUrl)
                    .result(e.getClass().getName())
                    .build());
            log.error(e.getMessage());
        }
    }
}