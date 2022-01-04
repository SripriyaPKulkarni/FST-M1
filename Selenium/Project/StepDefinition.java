package project;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {
	
	WebDriver driver;
	String pageTitle,pageHeader,headerImageURL,secondHeader;
	WebDriverWait driverWait;
	List<List<String>> jobDetails,jobDetailsWP;
	
	@Given("user Opens a browser")
	public void user_opens_a_browser() {
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driverWait=new WebDriverWait(driver,10);
	}
	@When("user Navigates to {string}")
	public void user_navigates_to(String url) {
		driver.navigate().to(url);
	}
	@When("user Get Page Title")
	public void user_get_page_title() {
		pageTitle=driver.getTitle();
		
	}
	@Then("Verify Page title matches with {string}")
	public void verify_page_title_matches_with(String title) {
		Assert.assertEquals(title, pageTitle.replaceAll("\\p{Pd}", "-"));
	}	
	@When("Get Page Header")
	public void get_page_header() {
	    pageHeader=driver.findElement(By.xpath("//h1[@itemprop='headline']")).getText();
	}
	@Then("verify Page heading matches with {string}")
	public void verify_page_heading_matches_with(String header) {
	    Assert.assertEquals(header, pageHeader);
	}
	@When("user gets the url of the header image")
	public void user_gets_the_url_of_the_header_image() {
		headerImageURL=driver.findElement(By.xpath("//img[@itemprop='image']")).getAttribute("src").toString();
	}
	@Then("Print header image url in Console")
	public void print_header_image_url_in_console() {
	    System.out.println("Image URL: "+headerImageURL);
	}
	@When("user gets the second heading on the page")
	public void user_gets_the_second_heading_on_the_page() {
	    secondHeader=driver.findElement(By.xpath("//div[@class='entry-content clear']/h2")).getText();
	}
	@Then("Verify second heading matches with {string}")
	public void verify_second_heading_matches_with(String header) {
	    Assert.assertEquals(header, secondHeader);
	}
	@When("user select the Jobs menu item from the Navigation Bar")
	public void user_select_the_jobs_menu_item_from_the_navigation_bar() {
	    driver.findElement(By.xpath("//li[@id='menu-item-24']/a[contains(text(),'Jobs')]")).click();
	}
	@Then("Verify Jobs Page title")
	public void verify_jobs_page_title() {
		Assert.assertEquals("Jobs - Alchemy Jobs", driver.getTitle().replaceAll("\\p{Pd}", "-"));
	}
	@Then("user search for a {string} job and wait for listings to show")
	public void user_search_for_a_particular_job_and_wait_for_listings_to_show(String jobName) {
	    driver.findElement(By.xpath("//input[@id='search_keywords']")).sendKeys(jobName);
	    driver.findElement(By.xpath("//input[@type='submit' and @value='Search Jobs']")).click();
	    By ele=By.xpath("//ul/li[@class='post-4182 job_listing type-job_listing status-publish hentry job-type-full-time']/a/div/h3");
	    driverWait.until(ExpectedConditions.visibilityOfElementLocated(ele));
	}
	@Then("user clicks and open any one of the jobs listed")
	public void user_clicks_and_open_any_one_of_the_jobs_listed() {
		driver.findElement(By.xpath("//ul/li[@class='post-4182 job_listing type-job_listing status-publish hentry job-type-full-time']/a/div/h3")).click();
	}
	@Then("user clicks the apply button and print the email to the console")
	public void user_clicks_the_apply_button_and_print_the_email_to_the_console() throws InterruptedException {
		driver.findElement(By.xpath("//input[@type='button' and @value='Apply for job']")).click();
		Thread.sleep(3000);
		System.out.println(driver.findElement(By.xpath("//a[@class='job_application_email']")).getText());
	}
	@When("user select the Post a Job menu item from the Navigation Bar")
	public void user_select_the_post_a_job_menu_item_from_the_navigation_bar() {
		driver.findElement(By.xpath("//li/a[contains(text(),'Post a Job')]")).click();
	}
	@When("user sign in to job listing page")
	public void user_sign_in_to_job_listing_page() {
		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.id("user_login")).sendKeys("root");
		driver.findElement(By.id("user_pass")).sendKeys("pa$$w0rd");
		driver.findElement(By.id("wp-submit")).click();		
	}
	@When("user fill up the neccessary details for job listing")
	public void user_fill_up_the_neccessary_details_for_job_listing(DataTable data) {
		jobDetails=data.asLists(String.class);
	    driver.findElement(By.id("job_title")).sendKeys(jobDetails.get(0).get(0));
	    driver.findElement(By.id("job_location")).sendKeys(jobDetails.get(0).get(1));
	    Select sel=new Select(driver.findElement(By.id("job_type")));
	    sel.selectByVisibleText(jobDetails.get(0).get(2));
	    driver.switchTo().frame("job_description_ifr");
	    driver.findElement(By.xpath("//body[@id='tinymce']/p")).sendKeys(jobDetails.get(0).get(3));
	    driver.switchTo().defaultContent();
	}
	@When("user clicks on the Preview button")
	public void user_clicks_on_the_preview_button() {
		driver.findElement(By.name("submit_job")).click();
	}
	@Then("user clicks on Submit Listing button")
	public void user_clicks_on_submit_listing_button() {
		driver.findElement(By.id("job_preview_submit_button")).click();
	}
	@Then("Verify job listing was posted in the job page")
	public void verify_job_listing_was_posted_in_the_job_page() {
		driver.findElement(By.xpath("//li[@id='menu-item-24']/a[contains(text(),'Jobs')]")).click();
		List<WebElement> jobsT=driver.findElements(By.xpath("//li/a/div/h3"));
		List<WebElement> jobsL=driver.findElements(By.xpath("//li/a/div[@class='location']"));
		Assert.assertEquals(jobDetails.get(0).get(0), jobsT.get(0).getText());
		Assert.assertEquals(jobDetails.get(0).get(1), jobsL.get(0).getText());
	}
	
	@When("user enters {string} in username field")
	public void user_enters_in_username_field(String userID) {
	    driver.findElement(By.id("user_login")).sendKeys(userID);
	}
	@When("user enters {string} in password field")
	public void user_enters_in_password_field(String pwd) {
		driver.findElement(By.id("user_pass")).sendKeys(pwd);
	}
	@When("user clicks on login button")
	public void user_clicks_on_login_button() {
		driver.findElement(By.id("wp-submit")).click();
	}
	@Then("Verify login successful")
	public void verify_login_successful() {
	    Assert.assertTrue(driver.findElement(By.xpath("//div[@id='wpbody']/div/div/h1[contains(text(),'Dashboard')]")).isDisplayed());
	}
	@When("user locates the left hand menu and clicks on Job Listings menu item")
	public void user_locates_the_left_hand_menu_and_clicks_on_job_listings_menu_item() {
	    driver.findElement(By.xpath("//li[@id='menu-posts-job_listing']/a/div[contains(text(),'Job Listings')]")).click();
	}
	@When("user clicks on Add New button")
	public void user_clicks_on_add_new_button() {
	    driver.findElement(By.xpath("//a[contains(text(),'Add New') and @class='page-title-action']")).click();
	}
	@When("user fills the necessary details for job listing in backend website")
	public void user_fills_the_necessary_details_for_job_listing_in_backend_website(DataTable data1) {
		jobDetailsWP=data1.asLists(String.class);
		driver.findElement(By.id("post-title-0")).sendKeys(jobDetailsWP.get(0).get(0));
		driver.findElement(By.name("_job_location")).sendKeys(jobDetailsWP.get(0).get(1));
		driver.findElement(By.name("_company_name")).sendKeys(jobDetailsWP.get(0).get(2));
	}
	@When("user clicks on Publish button")
	public void user_clicks_on_publish_button() throws InterruptedException {
	    driver.findElement(By.xpath("//button[contains(text(),'Publish')]")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//button[text()='Publish']")).click();
	    Thread.sleep(3000);
	  
	}
	@Then("Verify that the job listing was created")
	public void verify_that_the_job_listing_was_created() {
	    driver.findElement(By.xpath("//div[text()='is now live.']/a")).click();
	    Assert.assertEquals(jobDetailsWP.get(0).get(0), driver.findElement(By.xpath("//h1[@itemprop='headline']")).getText());
	    Assert.assertEquals(jobDetailsWP.get(0).get(1), driver.findElement(By.xpath("//li[@class='location']/a")).getText());
	}
	
	@After
	public void closeBrowser() {
		driver.quit();
	}

}
