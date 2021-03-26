package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.tomcat.jni.Time;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private NotePage notePage;
	private CredentialPage credentialPage;

	@Autowired
	private CredentialService credentialService;
	@Autowired
	private EncryptionService encryptionService;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void signupTest () {
		String username="oulutas2";
		String password="mad0132";
		driver.get("http://localhost:"+this.port+"/signup");
		signupPage = new SignupPage(driver);
		signupPage.apply("Orcun","ULUTAS",username,password);
		assertEquals("Username",signupPage.isLoginStatus());

		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);

		driver.get("http://localhost:"+this.port+"/home");
		assertEquals("Home",driver.getTitle());

	}
	@Test
	@Order(3)
	public void notePageTestAddaNoteAndVerify () {
		String username="oulutas2";
		String password="mad0132";
		String url="http://localhost:"+this.port+"/home";
		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);

		driver.get(url);
		notePage=new NotePage(driver);
		notePage.addNewNote("test1","desc1");
		driver.get(url);
		notePage.verify("test1","desc1");

		/*
		notePage.apply("test1","desc1","http://localhost:"+this.port+"/home","test2","desc2");
        addNewNote(title,desc);
        webDriver.get(url);
        verify(title,desc);
		 */
	}

	@Test
	@Order(4)
	public void notePageEditingAndVerify () {
		String username="oulutas2";
		String password="mad0132";
		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);

		driver.get("http://localhost:"+this.port+"/home");
		notePage=new NotePage(driver);
		notePage.edit("test2","desc2","http://localhost:"+this.port+"/home");
	}

	@Test
	@Order(5)
	public void notePageDelete() {
		String username="oulutas2";
		String password="mad0132";
		String url="http://localhost:"+this.port+"/home";
		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);
		driver.get("http://localhost:"+this.port+"/home");
		notePage=new NotePage(driver);
		notePage.delete(url);
	}


	@Test
	@Order(6)
	public void credentialPageTestAddVerify () {
		String newUrl="url1";
		String newUser="user1";
		String newPass="pass1";
		String secondUrl="url2";
		String secondUser="user2";
		String secondPass="pass2";
		String username="oulutas2";
		String password="mad0132";
		String url="http://localhost:"+this.port+"/home";

		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);

		driver.get("http://localhost:"+this.port+"/home");
		credentialPage=new CredentialPage(driver,credentialService,encryptionService);
		credentialPage.addNewCred(newUrl,newUser,newPass);
		driver.get(url);
		credentialPage.verify(newUrl,newUser,newPass,username);

		//credentialPage.apply(newUrl,newUser,newPass,secondUrl,secondUser,secondPass,url,username);
		// apply (String newUrl,String newUser,String newPass,String secondUrl,String secondUser,String secondPass,String url,String autUser)
	}
	@Test
	@Order(7)
	public void  setCredentialPageTestEditingAndVerify() {
		String newUrl="url1";
		String newUser="user1";
		String newPass="pass1";
		String secondUrl="url2";
		String secondUser="user2";
		String secondPass="pass2";
		String username="oulutas2";
		String password="mad0132";
		String url="http://localhost:"+this.port+"/home";

		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);

		driver.get("http://localhost:"+this.port+"/home");
		credentialPage=new CredentialPage(driver,credentialService,encryptionService);
		credentialPage.edit(url,secondUser,secondPass);

	}
	@Test
	@Order(8)
	public void setCredentialPageTestDelete() {
		String newUrl="url1";
		String newUser="user1";
		String newPass="pass1";
		String secondUrl="url2";
		String secondUser="user2";
		String secondPass="pass2";
		String username="oulutas2";
		String password="mad0132";
		String url="http://localhost:"+this.port+"/home";

		driver.get("http://localhost:"+this.port+"/login");
		loginPage=new LoginPage(driver);
		loginPage.apply(username,password);

		driver.get("http://localhost:"+this.port+"/home");
		credentialPage=new CredentialPage(driver,credentialService,encryptionService);
		credentialPage.delete(url);
	}



}
