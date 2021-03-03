package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

@Epic(value = "Search")
public class SearchTests extends CoreTestCase
{
    @Test
    @Features(value ={@Feature(value = "Search"),@Feature(value = "Articles")} )
    @DisplayName("testSearch")
    @Description("Search article in wiki")
    @Step("Start test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.screenshot(SearchPageObject.takeScreenshot("Search"));
        //ArticlePageObject.screenshot("Search_article");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
    @Test
    @Features(value ={@Feature(value = "Search"),@Feature(value = "Articles")} )
    @DisplayName("testCancelSearch")
    @Description("Cancel search")
    @Step("Start test testCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        //SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }
    @Test
    @Features(value ={@Feature(value = "Search"),@Feature(value = "Articles")} )
    @DisplayName("AmountOfNotEmptySearch")
    //@Description("")
    @Step("Start test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String search_line = "Linkin park discography";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        SearchPageObject.screenshot(SearchPageObject.takeScreenshot("Search"));
        assertTrue(
                "We found too few results",
                amount_of_search_results >0
        );

    }
    @Test
    @Features(value ={@Feature(value = "Search"),@Feature(value = "Articles")} )
    @DisplayName("No Result Of Search")
    //@Description("")
    @Step("Start test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "tfgvb";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }
}
