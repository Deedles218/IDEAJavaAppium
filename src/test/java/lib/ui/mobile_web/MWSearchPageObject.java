package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        //SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']//..//XCUIElementTypeSearchField";

        //SEARCH_CANCEL_BUTTON = "css:button.cancel";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULT_BY_TWO_SUBSTRINGS_TPL= "xpath://li[@title='{firstSUBSTRING}']//div[@class='wikidata-description'][contains(text(),'{secondSUBSTRING}')]";
                //"xpath://*[@name='{firstSUBSTRING}']//..//*[@name='{secondSUBSTRING}']//..";
    }
    public MWSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}

