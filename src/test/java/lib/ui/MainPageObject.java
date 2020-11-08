package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    //инициализируем драйвер
    protected RemoteWebDriver driver;
    //конструктор класса
    public MainPageObject( RemoteWebDriver driver)
    {
        this.driver = driver;
    }
    //метод для ожидания появления элемента со строкой поиска
    //передаем в методе xpath который надо найти, сообщение которое показываем в случае ошибки и время таймаута
    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {

        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);

        wait.withMessage(error_message + "/n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 5);
    }
    //метод ниже дожидается определенного элемента и кликает на него

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, 5);
        element.click();
        return element;
    }

    //метод sendKeys - пишет значение в строку поиска
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, 5);
        element.sendKeys(value);
        return element;
    }

    //метод убеждающийся что элемента нет на странице
    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    //метод очищающий элемент от данных введенных до нас
    public WebElement waitForElementAndClear( String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return (element);
    }

    //метод с простым свайпом снизу вверх
    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            // вводим относительные координаты для движений в action
            //определяем размер экрана девайса- переменная size с параметрами экрана
            Dimension size = driver.manage().window().getSize();
            //переменные по осям
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            //прописываем движения при свайпе движение от точки внизу экрана к верхней точке
            //переменную time of swipe передаем в wait action
            action
                    .press(PointOption.point(x, start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, end_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    //позволяет быстро свайпать вверх
    public void swipeUpQuick() {
        swipeUp(200);
    }
    //скрол для веб
    public void scrollWebPAgeUp(){
        if (Platform.getInstance().isMw()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPAgeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
    //сскролл до тех пока пока элемент не выйдет из поля зрения -для веб
    public void scrollWebPageTillElementNotVisible(String locator,String error_message, int max_swipes){
        int already_swiped =0;
        WebElement element = this.waitForElementPresent(locator,error_message);
        while (!this.isElementLocatedOnTheScreen(locator)){
            scrollWebPAgeUp();
            ++already_swiped;
            if (already_swiped>max_swipes)
                Assert.assertTrue(error_message, element.isDisplayed());
        }
    }

    //метод для свайпа до появления  определенного элемента на экране
    //дописываем ограничения для цикла чтобы он не свайпал бесконечно если не найдет элемент c int max_swipes
    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator);
        int already_swiped = 0; //в эту переменную записываются реальные свайпы
        //цикл т к элементов может быть найдено несколько,цикл будет работать пока функция не находит ни одного элемента
        while (driver.findElements(by).size() == 0) {
            //прописываем ограничения по свайпам
            if (already_swiped > max_swipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up.\n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;//плюсует свайпы к переменной
        }
    }
    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (this.isElementLocatedOnTheScreen(locator))
        {
            if(already_swiped >max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));}
            }
        swipeUpQuick();
        ++ already_swiped; //пока элемент не будет найден на экране мы будем использовать swipeUpQuick и добавлять значение already_swiped пока оно не превысит максимальное и тест не остановится
    }



    //метод определяющий есть ли элемент на странице или нет
    public boolean isElementLocatedOnTheScreen(String locator)
    {//находим элемент по локатору и получаем его расположение по оси У
        int element_location_by_y = this.waitForElementPresent(locator,"Cannot find element by locator",1).getLocation().getY();
        //поправка для веб
        if(Platform.getInstance().isMw()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y-= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight(); //длина всего экрана
        return element_location_by_y < screen_size_by_y;
    }
//красная кнопкa удаления для теста со статьев ios
    public void clickElementToTheRightUpperCorner(String locator,String error_message) {
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", error_message);
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();

            //правый верхний угол
            int point_to_click_x = (right_x + width) - 3;
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(PointOption.point(point_to_click_x, point_to_click_y)).perform();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
    //свайп для удаления статьи
    public void swipeElementToLeft(String locator, String error_message) {
        if (driver instanceof AppiumDriver) {
        WebElement element = waitForElementPresent(
                locator,
                error_message,
                10);

        //нужно обнаружить сам элемент по осям x y и передвинуть его по оси x
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        //координаты для свайпа
        int middle_y = (upper_y + lower_y) / 2; //середина элемента по оси y

        //инициализируем драйвер
        TouchAction action = new TouchAction((AppiumDriver)driver);
        action.press(PointOption.point(right_x, middle_y));
        //action.waitAction(300);
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));

        if(Platform.getInstance().isAndroid()) {
            action.moveTo(PointOption.point(left_x, middle_y));
        } else {
            int offset_x = (-1 * element.getSize().getWidth()); // свайп для айос на длину элемента
            action.moveTo(PointOption.point(offset_x, 0));
        }
        //action.moveTo(PointOption.point(left_x, middle_y));
        action.release();
        action.perform();
    } else {
            System.out.println("Method swipeElementToLeft does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public int getAmountElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        //возвращаем кол-во элементов найденных с помощью функции driver.findElements(by) после нахождения они записываются в список элементс и его размер мы и находим ниже
        return elements.size();
    }
    //метод определяет есть ли элемент на странице или нет
    public boolean isElementPresent(String locator){
        return getAmountElements(locator)>0;
    }
    // метод кликающий на элемент после анимации (веб) супер клик
    public void tryClickElementWithFewAttempts(String locator, String error_message, int among_of_attempts){
        int current_attempts = 0; //сколько раз уже успели кликнуть на момент запуска
        boolean need_more_attempts = true;
        while (need_more_attempts) {
            //лог для обработки ошибок
            try {
                this.waitForElementAndClick(locator,error_message,1);//если кликнуть не получится то до следующей строки цикл не дойдет
                need_more_attempts=false;
            } catch (Exception e) {
                if (current_attempts > among_of_attempts){
                    this.waitForElementAndClick(locator,error_message,1); //последняя попытка кликнуть
                }
            }
            ++ current_attempts;
        }


    }
    public void assertElementNotPresent(String locator,String error_message) {
        int amount_of_elements = getAmountElements(locator);
        if (amount_of_elements > 0) {
            String default_message ="An element '"+ locator + "' supposed to be not present";
            //ошибка
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    //Метод с ротацией
    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message,timeOutInSeconds);
        return element.getAttribute(attribute);
    }


    //метод для универсальных локаторов
    private  By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2); //записывает в переменную exploded
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        //сама логика разделения локаторов
        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        }else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator " + locator_with_type);
        }
    }
}

