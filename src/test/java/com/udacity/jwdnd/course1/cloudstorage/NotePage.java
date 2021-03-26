package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotePage {
    private WebDriver webDriver;
    private WebDriverWait wait ;
    @FindBy(xpath = "//*[@id=\"nav-notes-tab\"]")
    private WebElement notesTab;

    @FindBy(xpath = "//*[@id=\"nav-notes\"]/button")
    private WebElement addNote;

    @FindBy(xpath = "//*[@id=\"note-title\"]")
    private  WebElement addTitle;

    @FindBy(xpath = "//*[@id=\"note-description\"]")
    private WebElement addDescription;

    @FindBy(xpath = "//*[@id=\"noteModal\"]/div/div/div[3]/button[2]")
    private  WebElement addSave;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/th[1]")
    private WebElement titleLabel;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/th[2]")
    private WebElement descLablel;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td/button")
    private  WebElement editBtn;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td/a")
    private WebElement deleteBtn;

    public NotePage(WebDriver webDriver) {
        this.webDriver=webDriver;
        this.wait= new WebDriverWait(webDriver, 10);
        PageFactory.initElements(webDriver,this);
    }
    private void waitForElement(WebElement webElement){
        new WebDriverWait(webDriver,40)
                .until(ExpectedConditions.visibilityOf(webElement));
    }
    public void addNewNote(String title,String desc) {
            waitForElement(notesTab);
            notesTab.click();
            waitForElement(addNote);
            addNote.click();
            waitForElement(addTitle);
            addTitle.sendKeys(title);
            addDescription.sendKeys(desc);
            addSave.click();
    }

    public void verify(String title,String desc) {
        waitForElement(notesTab);
        notesTab.click();
        waitForElement(titleLabel);
        assertEquals(title,titleLabel.getText());
        assertEquals(desc,descLablel.getText());
    }
    public void edit (String title,String desc,String url) {
        waitForElement(editBtn);
        editBtn.click();
        waitForElement(addTitle);
        addTitle.clear();
        addTitle.sendKeys(title);
        addDescription.clear();
        addDescription.sendKeys(desc);
        addSave.click();
        webDriver.get(url);
        verify(title,desc);

    }
    public void delete (String url) {
        waitForElement(deleteBtn);
        deleteBtn.click();
        webDriver.get(url);

        assertEquals(true, webDriver.findElements(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td/a")).isEmpty());

    }

    public void  apply (String title,String desc,String url,String titleSecond,String descSecond) {


        addNewNote(title,desc);
        webDriver.get(url);
        verify(title,desc);
        edit(titleSecond,descSecond,url);
        delete(url);
    }

}
