package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private WebDriver webDriver;
    private String loginStatus;

    @FindBy(id="inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id="inputLastName")
    private WebElement inputLastName;

    @FindBy(xpath="//*[@id=\"inputUsername\"]")
    private WebElement inputUserName;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div[1]/form/button")
    private WebElement button;


    public SignupPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void  apply(String firstName,String lastName,String userName,String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUserName.sendKeys(userName);
        inputPassword.sendKeys(password);
        button.click();

        WebElement isOk = webDriver.findElement(By.xpath("/html/body/div[1]/form/div[1]"));
        loginStatus=isOk.getText();

    }

    public String isLoginStatus() {
        return loginStatus;
    }
}
