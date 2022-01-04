@EndToEndPart2
Feature: Activities_Backend.feature

	Background: Load Alchemy Jobs page
		Given user Opens a browser
		When user Navigates to "https://alchemy.hguy.co/jobs/wp-admin"
		And user enters "root" in username field
		And user enters "pa$$w0rd" in password field
		And user clicks on login button
	
	#Visit the site’s backend and login
	@Activity8
	Scenario: Login into the website backend
		Then Verify login successful
	
	#Visit the site’s backend and create a job listing
	@Activity9
	Scenario: Create a job listing using the backend
		And user locates the left hand menu and clicks on Job Listings menu item
		And user clicks on Add New button
		And user fills the necessary details for job listing in backend website
		|  Software Engineer | Bangalore,KA India | IBM |
		And user clicks on Publish button
		Then Verify that the job listing was created