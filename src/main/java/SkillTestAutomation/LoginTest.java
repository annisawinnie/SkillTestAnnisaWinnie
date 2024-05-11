package SkillTestAutomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginTest {
   private static WebDriver driver;
   private static Logger logger;
   private static WebElement getElementById(String id) {
        return driver.findElement(By.id(id));
    }
    private static WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
    public static void login(String username, String password) throws InterruptedException {
        WebElement usernameField = getElementById("user-name");
        WebElement passwordField = getElementById("password");
        WebElement loginButton = getElementById("login-button");

        usernameField.sendKeys(username);
        Thread.sleep(1000);
        passwordField.sendKeys(password);
        Thread.sleep(1000);
        loginButton.click();
        Thread.sleep(1000);
    }

    @BeforeTest
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        logger = Logger.getLogger(LoginTest.class.getName());
        driver.get("https://www.saucedemo.com/");
        java.util.logging.ConsoleHandler handler = new java.util.logging.ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
    }

    public static void assertLogin(Object Actual, Object Expected){
        Assert.assertEquals(Actual,Expected);
        logger.info("================ Assert equals ================");
        logger.info("Actual : " + Actual);
        logger.info("Expected : " + Expected);
        logger.info("Result : PASS");
        logger.info("===============================================");
    }

    @Test(testName = "input valid username & password: positive case")
    public void testLoginValid() throws InterruptedException {
        login("standard_user", "secret_sauce");
        WebElement title = getElementByXpath("//span[contains(text(),'Products')]");
        String actualResult = title.getText();
        String expectedResult = "Products";
        assertLogin(actualResult, expectedResult);
    }
    @Test(testName = "input invalid username & valid password: negative case")
    public void testLoginInvalidUsername() throws InterruptedException {
        login("invalid_username", "secret_sauce");
        WebElement errorMessage = getElementByXpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]");
        String actualResult = errorMessage.getText();
        String expectedResult = "Epic sadface: Username and password do not match any user in this service";
        assertLogin(actualResult, expectedResult);
    }
    @Test(testName = "input valid username & invalid password: negative case")
    public void testLoginInvalidPassword() throws InterruptedException {
        login("standard_user", "invalid_password");
        WebElement errorMessage = driver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]"));
        String actualResult = errorMessage.getText();
        String expectedResult = "Epic sadface: Username and password do not match any user in this service";
        assertLogin(actualResult, expectedResult);
    }
    @Test(testName = "input invalid username & invalid password: negative case")
    public void testLoginInvalidUsernamePassword() throws InterruptedException {
        login("invalid_username","invalid_password" );
        WebElement errorMessage = driver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]"));
        String actualResult = errorMessage.getText();
        String expectedResult = "Epic sadface: Username and password do not match any user in this service";
        assertLogin(actualResult, expectedResult);
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
