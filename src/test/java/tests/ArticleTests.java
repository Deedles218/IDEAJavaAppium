package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

@Epic(value = "Articles")
public class ArticleTests extends CoreTestCase
{
    @Test
    @Features(value ={@Feature(value = "Swipe"),@Feature(value = "Articles")} )
    @DisplayName("Compare Article title")
    @Description("Compare article title")
    @Step("Start test testCompareArticleTitle")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        assertEquals("We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }
    @Test
    @Features(value ={@Feature(value = "Swipe"),@Feature(value = "Articles")} )
    @DisplayName("testSwipeArticle")
    @Description("Swipe article to footer")
    @Step("Start test testSwipeArticle")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }
}
