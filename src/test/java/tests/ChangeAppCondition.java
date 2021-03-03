package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
@Epic(value = "AppCondition")
public class ChangeAppCondition extends CoreTestCase
{

    @Test
    @Feature(value = "App Conditions")
    @Feature(value = "General")
    @DisplayName("testChangeScreenOrientationOnSearchResult")
    @Description("Change rotation")
    @Step("Start test testChangeScreenOrientationOnSearchResult")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testChangeScreenOrientationOnSearchResult() {
        if (Platform.getInstance().isMw()){
            return;
        }
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_rotation
        );
        this.rotateScreenPortrait();
        String title_after_second_rotation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }
    @Test
    @Feature(value = "Background")
    @Feature(value = "General")
    @DisplayName("testCheckSearchArticleInBackground")
    @Description("App in background")
    @Step("Start test testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCheckSearchArticleInBackground()
    {
        if (Platform.getInstance().isMw()){
            return;
        }
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }
}
