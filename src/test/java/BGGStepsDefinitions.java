import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class BGGStepsDefinitions {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws MalformedURLException {

        //todo read from system properties?
        String seleniumServerAddress = "http://selenium:4444/wd/hub";

        if (driver == null) {
            driver = new RemoteWebDriver(new URL(seleniumServerAddress), new ChromeOptions());
            driver.manage().window().maximize();
        }
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Given("the user is successfully logged in")
    public void the_user_is_successfully_logged_in() {
        the_user_opens_the_board_game_geek_website();
        the_user_clicks_sign_in();
        the_user_fills_the_sign_in_form();
        the_user_submits_the_login_form();
        the_user_is_logged_in();
    }

    @Given("the user opens the BoardGameGeek website")
    public void the_user_opens_the_board_game_geek_website() {
        driver.get("https://boardgamegeek.com/");
        // synchronization
        wait.until(visibilityOfAllElementsLocatedBy(
                xpath(".//footer[@class='global-footer row']")));
    }

    @Given("the user clicks Sign In link")
    public void the_user_clicks_sign_in() {
        driver.findElement(xpath(".//button[contains(text(), 'Sign In')]")).click();
        // synchronization
        wait.until(visibilityOfAllElements(
                driver.findElement(xpath(".//input[@id='inputUsername']")),
                driver.findElement(xpath(".//input[@id='inputPassword']"))));
    }

    @Given("the user fills the Sign In form")
    public void the_user_fills_the_sign_in_form() {
        driver.findElement(xpath(".//input[@id='inputUsername']")).sendKeys("milanoid");
        driver.findElement(xpath(".//input[@id='inputPassword']")).sendKeys("677467432842276");
    }

    @Given("the user submits the Sign In form")
    public void the_user_submits_the_login_form() {
        wait.until(
                elementToBeClickable(xpath(".//form[@name='loginform']//button[@type='submit']"))
        ).click();
    }

    @Then("the user is logged in")
    public void the_user_is_logged_in() {
        // synchronization
        // 1. Sing In link is gone
        // 2. Geek Mail present
        // 3. Subscriptions present
        wait.until(invisibilityOfElementLocated(xpath(".//button[contains(text(), 'Sign In')]")));

        wait.until(visibilityOfAllElements(
                driver.findElement(xpath(".//subscription-notifications")),
                driver.findElement(xpath(".//geekmail-notifications"))
        ));
    }

    @Given("the user click its username to open menu")
    public void the_user_click_its_username_to_open_menu() {
        wait.until(
                elementToBeClickable(
                        xpath(".//div[contains(@class, 'logged-in')]//li[contains(@class, 'global-header-nav-user')]/button"
                        ))).click();

        // sync - wait for Sign Out link
        wait.until(
                elementToBeClickable(
                        xpath(".//div[@class='dropdown-menu dropdown-menu-right dropdown-menu-xl container-fluid']//li/a[@href='/logout']")));
    }

    // end of Background

    @Given("the user selects {string} from the menu")
    public void the_user_selects_from_the_menu(String item) {
        wait.until(
                elementToBeClickable(
                        xpath(String.format(".//div[@class='dropdown-menu dropdown-menu-right dropdown-menu-xl container-fluid']//li/a[contains(text(), '%s')]", item))))
                .click();

        // sync
        wait.until(visibilityOfAllElementsLocatedBy(xpath(".//div[@id='geekcollection_outerview']")));
    }

    @Given("the user opens game title {string}")
    public void the_user_opens_game_title(String title) {
        WebElement collectionObject1 = wait.until(elementToBeClickable(xpath(".//table[@class='collection_table']//div[@id='results_objectname1']")));
        String actualTitleName = collectionObject1.findElement(By.xpath(".//a")).getText();
        assert actualTitleName == title;
        collectionObject1.click();

        // sync - check title
        wait.until(textToBePresentInElementLocated(
                By.xpath("(.//div[@class='game-header-title-container'])[2]//div[@class='game-header-title-info']/h1/a"), title));
    }

    @When("the user opens polls and results for language dependence")
    public void the_user_opens_polls_and_results_for_language_dependence() {
        wait.until(elementToBeClickable(xpath(".//span[@item-poll-button='languagedependence']/button"))).click();

        // sync - check the slide out panel is open
        wait.until(visibilityOfElementLocated(xpath(".//div[@class='modal-content']//button[text()='Go to Poll']")));
    }

    @Then("the slide out panel is displayed with items")
    public void the_slide_out_panel_is_displayed_with_items(io.cucumber.datatable.DataTable dataTable) {
        List<String> list = dataTable.asList(String.class);
        for (int i = 0; i < list.size(); i++) {
            wait.until(visibilityOfElementLocated(xpath(String.format(".//div[@class='modal-content']//table//span[text()='%s']", list.get(i)))));
        }
    }

}
