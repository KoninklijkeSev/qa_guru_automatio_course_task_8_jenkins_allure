package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;

public class BaseClass {
    @BeforeAll
    static void setup() {
        // Listener make screenshots and save page source
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.startMaximized = true;

        // config for Java + Selenide
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // can see how working autotest in browser on selenoid
        capabilities.setCapability("enableVNC", true);
        // told start rec. video in browser on selenoid
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud:4444/wd/hub/";

        // config for Java + Selenium
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("browserName", "chrome");
//        capabilities.setCapability("browserVersion", "87.0");
//        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
//                "enableVNC", true,
//                "enableVideo", true
//        ));
//        RemoteWebDriver driver = new RemoteWebDriver(
//                URI.create("http://selenoid:4444/wd/hub").toURL(),
//                capabilities
//        );
    }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (System.getProperty("video_storage") != null)
            attachVideo();
        closeWebDriver();
    }
}
