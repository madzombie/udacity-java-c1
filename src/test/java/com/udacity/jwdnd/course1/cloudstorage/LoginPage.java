package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver webDriver;
    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "/html/body/div[1]/form/button")
    private WebElement button;

    public LoginPage(WebDriver webDriver) {
        this.webDriver=webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void apply(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        button.click();
    }
}
