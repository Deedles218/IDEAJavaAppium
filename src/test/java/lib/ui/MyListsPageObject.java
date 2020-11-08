package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

//внутри папки с сохраненками
abstract public class MyListsPageObject extends MainPageObject {
    //задаем константу
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            SWIPE_DELETE_BUTTON,
            ARTICLE_BY_DESCRIPTION_TPL,
            REMOVE_FROM_SAVED_BUTTON;


    //метод заменяющий значение константы на имя папки(заменяет шаблон)
    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    private static String getRemoveButtoneByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }
    private static String getLastArticleDescription(String substring)
    {
        return ARTICLE_BY_DESCRIPTION_TPL.replace("{SUBSTRING}",substring);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }


    //метод открыть папку
    public void openFolderByName(String name_of_folder) {
        //используем метод с заменой константы названия папки
        String folder_name_xpath = getSavedArticleXpathByTitle(name_of_folder);
        //тапнуть на элемент списка
        this.waitForElementAndClick(
                folder_name_xpath, //конкатинация
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    //метод ожидания статьи
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title  " + article_title, 15);
    }

    //убеждаемся что статья пропала(переиспользуем в методе со свайпом)
    public void waitForArticleDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }

//    //свайп статьи для удаления
//    public void swipeByArticleToDelete(String article_title) {
//        this.waitForArticleToAppearByTitle(article_title) ;
//        String article_xpath = getSavedArticleXpathByTitle(article_title);
//        //переписываем для веб
//        if (Platform.getInstance().isIOS()||Platform.getInstance().isAndroid()){
//            this.swipeElementToLeft(
//                    article_xpath,
//                    "Cannot find saved article"
//            );
//        } else {
//            String remove_locator = getRemoveButtoneByTitle(article_title);
//            this.waitForElementAndClick(
//                    remove_locator,
//                    "Cannot click button to remove article from saved",
//                    10
//            );
//        }
//        //находим статью и удаляем ее свайпом влево
//
//        if (Platform.getInstance().isIOS()) {
//        this.clickElementToTheRightUpperCorner(article_xpath,"Cannot find saved article");
//        }
//        if (Platform.getInstance().isMw()){
//            driver.navigate().refresh();
//        }
//    }
public void swipeByArticleToDelete(String article_title) {
    this.waitForArticleToAppearByTitle(article_title) ;
    String article_xpath = getSavedArticleXpathByTitle(article_title);
    //переписываем для веб
    if (Platform.getInstance().isIOS()||Platform.getInstance().isAndroid()){
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );
    } else {
        String remove_locator = getRemoveButtoneByTitle(article_title);
        this.waitForElementAndClick(
                remove_locator,
                "Cannot click button to remove article from saved",
                10
        );
    }
    //находим статью и удаляем ее свайпом влево

    if (Platform.getInstance().isIOS()) {
        this.clickElementToTheRightUpperCorner(article_xpath,"Cannot find saved article");
    }
    if (Platform.getInstance().isMw()){
        driver.navigate().refresh();
    }
}
    public void waitListsElementByDescription(String  article_title)
    {
        String article_description;
        if(Platform.getInstance().isAndroid()||Platform.getInstance().isIOS()){
            article_description = getLastArticleDescription(article_title);
        }
        else {
            //this.waitForArticleDisappearByTitle(article_title);
            this.waitForArticleToAppearByTitle(article_title);
             }
    }
}
