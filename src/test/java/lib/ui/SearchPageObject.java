package lib.ui;
//методы для поиска

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {
    //внесем константы строки поиска из тестов сюда
     protected static String
            SEARCH_INIT_ELEMENT ,
            SEARCH_INPUT,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TWO_SUBSTRINGS_TPL,
            SEARCH_CANCEL_BUTTON ,
            SEARCH_RESULT_ELEMENT ,
            SEARCH_EMPTY_RESULT_ELEMENT,
            ARTICLE_TITLE;

    //берем драйвер из MPO
    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    //метод для передачи подстроки в переменную(преобразует строки и не использует драйвер- поэтому можно сделать статичным
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTwoOptions(String firstSubstring, String secondSubstring) {
        String stringAfterFirstReplace = SEARCH_RESULT_BY_TWO_SUBSTRINGS_TPL.replace("{firstSUBSTRING}", firstSubstring);

        return stringAfterFirstReplace.replace("{secondSUBSTRING}", secondSubstring);
    }
    /* TEMPLATES METHODS */

    //метод инициализирующий поиск
    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    //метод ищущий кнопку х
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    //метод ожидания отсутствия кнопк  х в конце теста
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    //клик по кнопке отмены поиска
    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    //метод вводящий что то в строку поиска
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into  search input", 5);
    }

    //ожидание поисковой выдачи
    public void waitForSearchResult(String substring)
    //перепишем метод чтобы он принимал подстроку и подставляь ее в константу SEARCH_RESULT  после //*[@text=
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    //кликаем статью из поиска по substring
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    //метод для определения количества статей
    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request ",
                15
        );
        //метод возвращает количество элементов
        return this.getAmountElements(SEARCH_RESULT_ELEMENT);
    }

    //метод убеждающийся что на странице нет результатов поиска
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    //метод убеждающийся что элементов на странице нет
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    //метод убеждающийся что на странице нет результатов поиска
    public void waitForArticleTitle() {
        this.waitForElementPresent(ARTICLE_TITLE, "Cannot find article title", 15);
    }

    //метод ожидающий элемент по тайтлу и описанию

    public String waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchElementByTwoOptions(title, description);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with title = '" + title + "' and description = '" + description + "'");
        return search_result_xpath;
    }
}