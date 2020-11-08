package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;
import sun.security.util.Password;

public class AuthorizationPageObject extends MainPageObject {
    private  static final String
    LOGIN_BUTTON ="xpath://body/div//a[text()='Log in']",
    LOGIN_INPUT = "css:#wpName1",
    PASSWORD_INPUT = "css:#wpPassword1",
    SUBMIT_BUTTON = "css:button#wpLoginAttempt"; //wpLoginAttempt
    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }
    //методы для авторизации в веб
    public void clickAuthButton() throws InterruptedException {
//        System.out.println(LOGIN_BUTTON);
        Thread.sleep(1000);
//        if (Platform.getInstance().isMw()){
//            this.tryClickElementWithFewAttempts(
//                    LOGIN_BUTTON,
//                    "Cannot find button Log in",
//                    5) ;
//        }else{
//            this.waitForElementPresent(LOGIN_BUTTON,"Cannot find auth button",20);
//        }
//        //System.out.println(LOGIN_BUTTON);
//        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button",15);
//    }
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button",5);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find click auth button",5);}
//
//        if (Platform.getInstance().isMw()) {
//            this.tryClickElementWithFewAttempts(
//                    LOGIN_BUTTON,
//                    "Cannot find auth button",
//                    5
//            );
//            }
//        //this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find click auth button",5);





    public void enterLoginData(String login, String password) throws InterruptedException {
        Thread.sleep(5000);
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put login to the login input",5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password,"Cannot find and put password to the password input",5);
    }
    public void submitForm(){
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button",5);
    }
}
