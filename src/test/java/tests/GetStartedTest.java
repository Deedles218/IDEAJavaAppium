package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;
//тест по прохождению welcome страниц в приложении на ios
public class GetStartedTest extends CoreTestCase {
    @Test
    public void testPassThroughWelcome() {
        // скип теста если платформа андройд
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
