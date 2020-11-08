package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;

//тесты для статей
public class ArticleTests extends CoreTestCase
{
    //сравнение названия статьи с ожидаемым и отдавать ошибку если не совпадает
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        //передадим результат работы метода в переменную чтобы использовать ее в ассерте
        String article_title = ArticlePageObject.getArticleTitle();
        //assert
        assertEquals("We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }
    //Простой свайп
    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        //метод который свайпает страницу вниз пока не найдет определенный элемент
        ArticlePageObject.swipeToFooter();
    }
}
