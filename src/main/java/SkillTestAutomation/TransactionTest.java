package SkillTestAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TransactionTest{
    private static WebDriver driver;
    private Logger logger;
    private static WebElement getElementById(String id) {
        return driver.findElement(By.id(id));
    }
    private WebElement getElementByXpath(String xpath) {
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
    public void openCartBtn() throws InterruptedException {
        WebElement openCartBtn = getElementById("shopping_cart_container");
        openCartBtn.click();
        Thread.sleep(1000);
    }
    public void addToCart() throws InterruptedException {
        WebElement addToCartBtn = getElementById("add-to-cart-sauce-labs-backpack");
        addToCartBtn.click();
        Thread.sleep(1000);
        openCartBtn();
    }
    public void addMoreThanOneProductToCart() throws InterruptedException {
        WebElement addProduct1ToCartBtn = getElementById("add-to-cart-sauce-labs-backpack");
        addProduct1ToCartBtn.click();
        Thread.sleep(1000);
        WebElement addProduct2ToCartBtn = getElementById("add-to-cart-sauce-labs-bike-light");
        addProduct2ToCartBtn.click();
        Thread.sleep(1000);
        WebElement addProduct3ToCartBtn = getElementById("add-to-cart-sauce-labs-bolt-t-shirt");
        addProduct3ToCartBtn.click();
        Thread.sleep(1000);
        openCartBtn();
    }
    public void removeMoreThanOneProductToCart() throws InterruptedException {
        WebElement removeProduct1ToCartBtn = getElementById("remove-sauce-labs-backpack");
        removeProduct1ToCartBtn.click();
        Thread.sleep(1000);
        WebElement removeProduct2ToCartBtn = getElementById("remove-sauce-labs-bike-light");
        removeProduct2ToCartBtn.click();
        Thread.sleep(1000);
        WebElement removeProduct3ToCartBtn = getElementById("remove-sauce-labs-bolt-t-shirt");
        removeProduct3ToCartBtn.click();
        Thread.sleep(1000);
        openCartBtn();
    }
    public void fillAllFieldsOrderingInformation() throws InterruptedException {
        addMoreThanOneProductToCart();
        openCartBtn();
        WebElement checkoutBtn = getElementById("checkout");
        checkoutBtn.click();
        Thread.sleep(1000);
        WebElement firstNameField = getElementById("first-name");
        firstNameField.sendKeys("Annisa");
        Thread.sleep(1000);
        WebElement lastNameField = getElementById("last-name");
        lastNameField.sendKeys("Winnie");
        Thread.sleep(1000);
        WebElement postalCodeField = getElementById("postal-code");
        postalCodeField.sendKeys("54321");
        Thread.sleep(1000);
        WebElement continueCheckoutBtn = getElementById("continue");
        continueCheckoutBtn.click();
        Thread.sleep(1000);
    }
    public void emptyFirstNameFields() throws InterruptedException {
        addToCart();
        openCartBtn();
        WebElement checkoutBtn = getElementById("checkout");
        checkoutBtn.click();
        Thread.sleep(2000);
        WebElement lastNameField = getElementById("last-name");
        lastNameField.sendKeys("Winnie");
        Thread.sleep(2000);
        WebElement postalCodeField = getElementById("postal-code");
        postalCodeField.sendKeys("54321");
        Thread.sleep(3000);
        WebElement continueCheckoutBtn = getElementById("continue");
        continueCheckoutBtn.click();
        Thread.sleep(2000);
    }
    public void emptyLastNameFields() throws InterruptedException {
        addToCart();
        openCartBtn();
        WebElement checkoutBtn = getElementById("checkout");
        checkoutBtn.click();
        Thread.sleep(2000);
        WebElement firstNameField = getElementById("first-name");
        firstNameField.sendKeys("Annisa");
        Thread.sleep(2000);
        WebElement postalCodeField = getElementById("postal-code");
        postalCodeField.sendKeys("54321");
        Thread.sleep(3000);
        WebElement continueCheckoutBtn = getElementById("continue");
        continueCheckoutBtn.click();
        Thread.sleep(2000);
    }
    public void emptyPostalCodeFields() throws InterruptedException {
        addToCart();
        openCartBtn();
        WebElement checkoutBtn = getElementById("checkout");
        checkoutBtn.click();
        Thread.sleep(2000);
        WebElement firstNameField = getElementById("first-name");
        firstNameField.sendKeys("Annisa");
        Thread.sleep(2000);
        WebElement lastNameField = getElementById("last-name");
        lastNameField.sendKeys("Winnie");
        Thread.sleep(2000);
        WebElement continueCheckoutBtn = getElementById("continue");
        continueCheckoutBtn.click();
        Thread.sleep(2000);
    }
    public void validatePriceTotal() throws InterruptedException {
        fillAllFieldsOrderingInformation();
        WebElement itemTotalElement = driver.findElement(By.xpath("//div[@data-test='subtotal-label']"));
        String itemTotalText = itemTotalElement.getText();
        double itemTotalValue = Double.parseDouble(itemTotalText.replaceAll("[^0-9.]", ""));
        WebElement taxElement = driver.findElement(By.xpath("//div[@data-test='tax-label']"));
        String taxText = taxElement.getText();
        double taxValue = Double.parseDouble(taxText.replaceAll("[^0-9.]", ""));
        WebElement totalElement = driver.findElement(By.xpath("//div[@data-test='total-label']"));
        String totalText = totalElement.getText();
        double totalValue = Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));

        double expectedItemTotal = 55.97;
        double expectedTax = 4.48;
        double expectedTotal = 60.45;
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
    public void assertTransaction(Object Actual, Object Expected, String s){
        Assert.assertEquals(Actual,Expected);
        logger.info("================ Assert equals ================");
        logger.info("Actual : " + Actual);
        logger.info("Expected : " + Expected);
        logger.info("Result : PASS");
        logger.info("===============================================");
    }
    @Test(testName = "Add product to Cart")
    public void testAddToCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        addToCart();
        WebElement cartItemCount = getElementById("shopping_cart_container");
        String actualItemCountText = cartItemCount.getText();
        int actualItemCount = Integer.parseInt(actualItemCountText);
        int expectedItemCount = 1;
        assertTransaction(actualItemCount, expectedItemCount, "Jumlah produk dalam keranjang tidak sesuai");
    }
    @Test(testName = "Add more than one product to Cart")
    public void testAddMoreThanOneProductToCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        addMoreThanOneProductToCart();
        WebElement cartItemCount = getElementById("shopping_cart_container");
        String actualItemCountText = cartItemCount.getText();
        int actualItemCount = Integer.parseInt(actualItemCountText);
        int expectedItemCount = 3;
        assertTransaction(actualItemCount, expectedItemCount, "Jumlah produk dalam keranjang tidak sesuai");
    }
    @Test(testName = "Remove product to Cart")
    public void testRemoveFromCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        addToCart();
        WebElement removeFromCartBtn = getElementById("remove-sauce-labs-backpack");
        removeFromCartBtn.click();
        Thread.sleep(2000);

        WebElement cartItemCount = getElementById("shopping_cart_container");
        String actualItemCountText = cartItemCount.getText();
        String expectedItemCount = "";
        assertTransaction(actualItemCountText, expectedItemCount, "Jumlah produk dalam keranjang tidak sesuai");
    }
    @Test(testName = "Remove all product that has been added to Cart")
    public void testRemoveMoreThanOneProductFromCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        addMoreThanOneProductToCart();
        removeMoreThanOneProductToCart();

        WebElement cartItemCount = getElementById("shopping_cart_container");
        String actualItemCountText = cartItemCount.getText();
        String expectedItemCount = "";
        assertTransaction(actualItemCountText, expectedItemCount, "Jumlah produk dalam keranjang tidak sesuai");
    }
    @Test(testName = "Remove some products that have been added to the cart")
    public void testRemoveSomeProductFromCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        addMoreThanOneProductToCart();
        WebElement removeFromCartBtn = getElementById("remove-sauce-labs-backpack");
        removeFromCartBtn.click();
        Thread.sleep(2000);
        WebElement cartItemCount = getElementById("shopping_cart_container");
        String actualItemCountText = cartItemCount.getText();
        int actualItemCount = Integer.parseInt(actualItemCountText);
        int expectedItemCount = 2;
        assertTransaction(actualItemCount, expectedItemCount, "Jumlah produk dalam keranjang tidak sesuai");
    }
    @Test(testName = "Close Cart List Page")
    public void testOpenCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        addToCart();
        WebElement continueShoppingBtn = getElementById("continue-shopping");
        continueShoppingBtn.click();
        WebElement homePage = getElementByXpath("//span[contains(text(),'Products')]");
        String actualResult = homePage.getText();
        String expectedResult = "Products";
        assertTransaction(actualResult, expectedResult, "Jumlah produk dalam keranjang tidak sesuai");
    }
    @Test(testName = "Empty First name field on the ordering information form")
    public void testEmptyFirstNameField() throws InterruptedException {
        login("standard_user", "secret_sauce");
        emptyFirstNameFields();
        WebElement errorMessageEmptyFirstName = getElementByXpath("//h3[normalize-space()='Error: First Name is required']");
        String actualResult = errorMessageEmptyFirstName.getText();
        String expectedResult = "Error: First Name is required";
        assertTransaction(actualResult, expectedResult, "Hasil sudah sesuai");
    }
    @Test(testName = "Empty Last name field on the ordering information form")
    public void testEmptyLastNameField() throws InterruptedException {
        login("standard_user", "secret_sauce");
        emptyLastNameFields();
        WebElement errorMessageEmptyLastName = getElementByXpath("//h3[normalize-space()='Error: Last Name is required']");
        String actualResult = errorMessageEmptyLastName.getText();
        String expectedResult = "Error: Last Name is required";
        assertTransaction(actualResult, expectedResult, "Hasil sudah sesuai");
    }
    @Test(testName = "Empty Postal Code field on the ordering information form")
    public void testEmptyPostalCodeField() throws InterruptedException {
        login("standard_user", "secret_sauce");
        emptyPostalCodeFields();
        WebElement errorMessageEmptyLastName = getElementByXpath("//h3[normalize-space()='Error: Postal Code is required']");
        String actualResult = errorMessageEmptyLastName.getText();
        String expectedResult = "Error: Postal Code is required";
        assertTransaction(actualResult, expectedResult, "Hasil sudah sesuai");
    }
    @Test(testName = "Fill in all field on the ordering information form")
    public void testFillAllFieldsOrderingInformation() throws InterruptedException {
        login("standard_user", "secret_sauce");
        fillAllFieldsOrderingInformation();
        WebElement overviewPage = getElementByXpath("//span[contains(text(),'Checkout: Overview')]");
        String actualResult = overviewPage.getText();
        String expectedResult = "Checkout: Overview";
        assertTransaction(actualResult, expectedResult, "Hasil sudah sesuai");
    }
    @Test(testName = "Validate Price Total")
    public void ValidatePriceTotal() throws InterruptedException {
        login("standard_user", "secret_sauce");
        fillAllFieldsOrderingInformation();
        WebElement itemTotalElement = driver.findElement(By.xpath("//div[@data-test='subtotal-label']"));
        String itemTotalText = itemTotalElement.getText();
        double itemTotalValue = Double.parseDouble(itemTotalText.replaceAll("[^0-9.]", ""));
        WebElement taxElement = driver.findElement(By.xpath("//div[@data-test='tax-label']"));
        String taxText = taxElement.getText();
        double taxValue = Double.parseDouble(taxText.replaceAll("[^0-9.]", ""));
        WebElement totalElement = driver.findElement(By.xpath("//div[@data-test='total-label']"));
        String totalText = totalElement.getText();
        double totalValue = Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));

        double expectedItemTotal = 55.97;
        double expectedTax = 4.48;
        double expectedTotal = 60.45;
        assertTransaction(itemTotalValue, expectedItemTotal, "Item total tidak sesuai");
        assertTransaction(taxValue, expectedTax, "Pajak tidak sesuai");
        assertTransaction(totalValue, expectedTotal, "Total tidak sesuai");
    }
    @Test(testName = "successfully completed the transaction")
    public void testSuccessCompletedTheTransaction() throws InterruptedException {
        login("standard_user", "secret_sauce");
        validatePriceTotal();
        WebElement finishBtn = getElementById("finish");
        finishBtn.click();
        WebElement completedTransactions = getElementByXpath("//h2[normalize-space()='Thank you for your order!']");
        String actualResult = completedTransactions.getText();
        String expectedResult = "Thank you for your order!";
        assertTransaction(actualResult, expectedResult, "Hasil sudah sesuai");
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
