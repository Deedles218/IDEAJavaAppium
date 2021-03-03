package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;
@Epic(value = "GetStarted")

public class GetStartedTest extends CoreTestCase {
    @Test
    @Feature(value = "Welcome")
    @DisplayName("PassThroughWelcome")
    @Description("Pass through welcome screens")
    @Step("Start test testPassThroughWelcome")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testPassThroughWelcome() {
        if ((Platform.getInstance().isAndroid())|| (Platform.getInstance().isMw())){
            return;
        }
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);
            WelcomePage.waitForLearnMoreLink();
            WelcomePage.clickNextButton();

            WelcomePage.waitForNewWaysText();
            WelcomePage.clickNextButton();

            WelcomePage.waitForAddOrEditPreferredLangText();
            WelcomePage.clickNextButton();

            WelcomePage.waitForLearnMoreAboutDataCollectedText();
            WelcomePage.clickGetStartedButton();
        }
    }
