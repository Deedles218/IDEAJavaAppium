package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    //тест с сохранением одной статьи
    private static final String name_of_folder = "Learning programming";
    private static final String login="AutomatorQA";
    private static final String password = "Automator2020";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {


        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle(); //отдельная переменная чтобы в  конце теста проверить название статьи

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        Thread.sleep(5000);

if(Platform.getInstance().isMw()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
    Thread.sleep(5000);
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();
    Thread.sleep(5000);
            ArticlePageObject.waitForTitleElement();
            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );
}
        Thread.sleep(5000);
        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickCancelSearch();
        }
//до этого момента все работает

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        if (Platform.getInstance().isIOS()) {
            NavigationUI.closeIOSSync();
        }
        ArticlePageObject.closeArticle();
//добавляем метод для веба
        NavigationUI.openNavigation();
        Thread.sleep(5000);
        NavigationUI.clickMyLists();
        Thread.sleep(5000);

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder);
        }
        Thread.sleep(5000);
        //метод со свайпом + появление статьи и проверка того что статья удалилась
        MyListPageObject.swipeByArticleToDelete(article_title);
    }



    //Ex11
    public void testSaveSecondArticle() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);

        if (Platform.getInstance().isIOS()) {
            NavigationUI.closeIOSSync();
        }
        ArticlePageObject.closeArticle();

        //добавляем вторую статью
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("rogramming language");
        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addSecondArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI.clickMyLists();
        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder);
        }
        //метод со свайпом
        MyListPageObject.swipeByArticleToDelete(article_title);
        //проверка что вторая статья осталась
        MyListPageObject.waitListsElementByDescription(article_title);
    }
}

