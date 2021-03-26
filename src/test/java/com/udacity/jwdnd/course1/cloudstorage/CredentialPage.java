package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CredentialPage {
    private WebDriver webDriver;

    private CredentialService credentialService;

    private EncryptionService encryptionService;

    @FindBy(xpath = "//*[@id=\"nav-credentials-tab\"]")
    private WebElement credTab;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]/button")
    private WebElement addBtn;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private  WebElement editBtn;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteBtn;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/th")
    private WebElement urlLabel;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[2]")
    private WebElement userLabel;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[3]")
    private WebElement passwordLabel;

    @FindBy(xpath = "//*[@id=\"credential-url\"]")
    private WebElement urlEdit;

    @FindBy(xpath = "//*[@id=\"credential-username\"]")
    private WebElement userEdit;

    @FindBy(xpath = "//*[@id=\"credential-password\"]")
    private WebElement passwordEdit;

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
    private WebElement saveChangeBtn;

    @FindBy(xpath = "//*[@id=\"credential-url\"]")
    private  WebElement addUrl;

    @FindBy(xpath = "//*[@id=\"credential-username\"]")
    private WebElement addUSer;

    @FindBy(xpath = "//*[@id=\"credential-password\"]")
    private WebElement addPass;

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
    private WebElement addNewBtn;
    @FindBy(id = "open-credentials-modal")
    private WebElement openCredentialsModal;


    public CredentialPage(WebDriver webDriver,CredentialService credentialService,EncryptionService encryptionService) {
        this.webDriver=webDriver;
        this.credentialService=credentialService;
        this.encryptionService=encryptionService;
        PageFactory.initElements(webDriver,this);
    }
    private void waitForElement(WebElement webElement){
        new WebDriverWait(webDriver,40)
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public void openCredentialsModal(){
        waitForElement(openCredentialsModal);
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",openCredentialsModal);
    }

    public void addNewCred (String newUrl,String newUser,String newPass) {
        waitForElement(credTab);
        credTab.click();
        waitForElement(addBtn);
        addBtn.click();
        waitForElement(addUrl);
        addUrl.sendKeys(newUrl);
        //openCredentialsModal();
        waitForElement(addUSer);
        addUSer.sendKeys(newUser);
        waitForElement(addPass);
        addPass.sendKeys(newPass);
        waitForElement(addNewBtn);
        addNewBtn.click();
    }

    public void verify(String url,String user,String password,String authUser) {
        waitForElement(credTab);
        credTab.click();
        waitForElement(urlLabel);
        assertEquals(url,urlLabel.getText());
        assertEquals(user,userLabel.getText());
        Credential tempCred = credentialService.getLastRecordByUserId(authUser);

        String clearPass=encryptionService.decryptValue(tempCred.getPassword(),tempCred.getKey());

        System.out.println(clearPass+":"+password);
        assertEquals(clearPass,password);

    }

    public void edit(String url,String user,String password) {
        waitForElement(credTab);
        credTab.click();
        waitForElement(editBtn);
        editBtn.click();
        waitForElement(urlEdit);
        urlEdit.clear();
        urlEdit.sendKeys(url);
        userEdit.clear();
        userEdit.sendKeys(user);
        passwordEdit.clear();
        passwordEdit.sendKeys(password);
        saveChangeBtn.click();

    }

    public void delete(String url) {
        waitForElement(credTab);
        credTab.click();
        waitForElement(deleteBtn);
        deleteBtn.click();
        webDriver.get(url);
        waitForElement(credTab);
        credTab.click();
        assertEquals(true, webDriver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")).isEmpty());
    }

    public void apply (String newUrl,String newUser,String newPass,String secondUrl,String secondUser,String secondPass,String url,String autUser){
        addNewCred(newUrl,newUser,newPass);
        webDriver.get(url);
        verify(newUrl,newUser,newPass,autUser);
        edit(secondUrl,secondUser,secondPass);
        webDriver.get(url);
        verify(secondUrl,secondUser,secondPass,autUser);
    }




}
