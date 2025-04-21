package CreateWithPOJOAndLombok;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateContactTestWithPOJO {

	public String generateRandomEmailId() {
		String emailId = "testapi_" + System.currentTimeMillis() + "@testemail.com";
		return emailId;
	}

	@Test
	public void addContactTest() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		// 1. POST - Create an user using POJO with Jackson library(added in pom.xml)
		CreateContacts contact = new CreateContacts("TestFN", "TestLN", "1992-02-02", generateRandomEmailId(),
				"8005554242", "13 School St.", "Apt. 5", "Toronto", "ON", "B1B1B1", "Canada");

		Object contactId = given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxOTgzNTh9.YK2QCt14E1mPelilpIarRUoevV7rcssNPILNiOw73Gc")
				.contentType(ContentType.JSON)
				// No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson
				// API must be included in pom.xml
				.body(contact) // Serialisation
				.when().post("/contacts").then().log().all().statusCode(201).and().statusLine("HTTP/1.1 201 Created")
				.extract().path("_id");
		
		System.out.println("==================================================");

		System.out.println("User Id =" + contactId);

		System.out.println("==================================================");

		// 2. GET-GetUser with created UserId -/users/userId

		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxOTgzNTh9.YK2QCt14E1mPelilpIarRUoevV7rcssNPILNiOw73Gc")
				.when().get("/contacts/" + contactId).then().log().all().assertThat().statusCode(200).and()
				.body("_id", equalTo(contactId));// Correlation
	}

}
