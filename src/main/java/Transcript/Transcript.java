package Transcript;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;


public class Transcript {

    File jsonFile = new File("C:\\temp\\ltu.json");


    //Method for the configurations for the tests.
    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome --headless";
        Configuration.baseUrl = "https://www.ltu.se/";
        Configuration.timeout = 5000;
        Configuration.proxyEnabled = true;


    }
    @After
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
    }

    @Test
    public void testDownloadTranscript() throws FileNotFoundException {

        open("https://www.ltu.se/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        String expectedTitle = "Luleå tekniska universitet, LTU";
        String actualTitle = title();
        assertEquals(expectedTitle, actualTitle);


        $(byXpath("//*[@id=\"CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll\"]")).click();
        //Navigate onwards to "Student".
        $(byXpath("//*[@id=\"main-nav\"]/div[3]/div/a[1]")).click();
        //Navigate onwards towards transcripts.
        $(byXpath("//*[@id=\"maincontent\"]/div[1]/div/div[2]/div/div/div/div/ul/li[1]/a/div")).click();


        // Click the "find your institution" button.
        $(byXpath("/html/body/ladok-root/div/main/div/ladok-inloggning/div/div/div/div/div/div/div/ladok-student/div[1]/a/div/div[2]/span[2]")).click();
        // Search for LTU.
        $(byXpath("//*[@id=\"searchinput\"]")).setValue("ltu");
        //click on the search-result.
        $(byXpath("//*[@id=\"ds-search-list\"]/a/li/div/div[1]")).click();


        try {
            //Create the objectmapper and readability for the json-file containing credentials.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            //Fetch the username and password from the credentials file.
            String username = jsonNode.get("ltuCredentials").get("username").asText();
            String password = jsonNode.get("ltuCredentials").get("password").asText();



            //Enter the login information.
            $("#username").val(username);
            $("#password").val(password);
        } catch (IOException e) {
            System.out.println("Error reading credentials from JSON file: " + e.getMessage());
        }
        //Click on login
        $(byXpath("//*[@id=\"fm1\"]/section[3]/input[4]")).click();
        //Click on the "Transcript-button" in the side-menu.
        $(byXpath("//*[@id=\"sidomeny-ul\"]/li[3]/ladok-behorighetsstyrd-nav-link/a")).click();

        //Click on the created Transcript to download it.
        $(byXpath("//*[@id=\"main\"]/div/ladok-intyg/ladok-listning-av-skapade-intyg/div/div/ladok-accordion/div/ladok-list-kort[1]/div/div[1]/div/div[1]/a")).click();

        SelenideElement downloadButton = $(byXpath("//*[@id=\"main\"]/div/ladok-intyg/ladok-listning-av-skapade-intyg/div/div/ladok-accordion/div/ladok-list-kort[1]/div/div[1]/div/div[1]/a"));
        if (downloadButton.exists()) {
            System.out.println("You successfully downloaded the transcript");
        } else {
            System.out.println("Error downloading the transcript");
        }

        // Download the transcript and move it to the target folder
        File downloadedFile = downloadButton.download();
        File targetFolder = new File("target");
        downloadedFile.renameTo(new File(targetFolder, "intyg.pdf"));

        // Print a success message
        System.out.println("Transcript downloaded and moved to the target folder.");


        //Log out
        $(byXpath("//*[@id=\"sidomeny\"]/div[1]/ul[3]/li/a")).click();



    }




    //Test to navigate to the Transcript button, and once it has been verified that the button exists, create one.


    @Test
    @Ignore
    public void testCreateTranscript() {

        open("https://www.ltu.se/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        String expectedTitle = "Luleå tekniska universitet, LTU";
        String actualTitle = title();
        assertEquals(expectedTitle, actualTitle);

        $(byXpath("//*[@id=\"CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll\"]")).click();
        //Navigate onwards to "Student".
        $(byXpath("//*[@id=\"main-nav\"]/div[3]/div/a[1]")).click();
        //Navigate onwards towards transcripts.
        $(byXpath("//*[@id=\"maincontent\"]/div[1]/div/div[2]/div/div/div/div/ul/li[1]/a/div")).click();
        // Click the "find your institution" button.
        $(byXpath("/html/body/ladok-root/div/main/div/ladok-inloggning/div/div/div/div/div/div/div/ladok-student/div[1]/a/div/div[2]/span[2]")).click();
        // Search for LTU.
        $(byXpath("//*[@id=\"searchinput\"]")).setValue("ltu");
        //click on the search-result.
        $(byXpath("//*[@id=\"ds-search-list\"]/a/li/div/div[1]")).click();

        try {
            //Create the objectmapper and readability for the json-file containing credentials.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            //Fetch the username and password from the credentials file.
            String username = jsonNode.get("ltuCredentials").get("username").asText();
            String password = jsonNode.get("ltuCredentials").get("password").asText();



            //Enter the login information.
            $("#username").val(username);
            $("#password").val(password);
        } catch (IOException e) {
            System.out.println("Error reading credentials from JSON file: " + e.getMessage());
        }
        //Click on login
        $(byXpath("//*[@id=\"fm1\"]/section[3]/input[4]")).click();
        //Click on the "Transcript-button" in the side-menu.
        $(byXpath("//*[@id=\"sidomeny-ul\"]/li[3]/ladok-behorighetsstyrd-nav-link/a")).click();

        //Checks and verifies that the button exists.
        $(byXpath("//*[@id=\"main\"]/div/ladok-intyg/ladok-skapa-intyg-knapp/div/button")).shouldBe(Condition.visible).click();
        if ($(byXpath("//*[@id=\"main\"]/div/ladok-intyg/ladok-skapa-intyg-knapp/div/button")).exists()) {
            System.out.println("The button to create a transcript has been located.");
        }
        //Create the transcript.
        $(byXpath("//*[@id=\"intygstyp\"]")).click();
        $(byXpath("//*[@id=\"intygstyp\"]/option[2]")).click();
        $("#start").val("2022-09-01");
        $("#slut").val("2023-04-01");
        $(byXpath("//*[@id=\"main\"]/div/ladok-skapa-intyg/ladok-card/div/div/ladok-card-body/div[3]/div/form/div[3]/div/ladok-skapa-intyg-knapprad/div/button[1]/span")).click();

        String successMessage = "Intyget skapades.";
        $(byText(successMessage)).shouldBe(Condition.visible);
        if ($(byText(successMessage)).exists()) {
            System.out.println("Transcript successfully created.");
        }


    }







}