package CreateWithPOJOAndLombok;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateAContactWithLombokTest {

	public String generateRandomEmailId() {
		String emailId = "testapi_" + System.currentTimeMillis() + "@testemail.com";
		return emailId;
	}

	@Test
	public void addContactTestWithLombok() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		// 1. POST - Create an user using POJO with Lombok(added in pom.xml)
		ContactLombak contact = new ContactLombak("TestFN", "TestLN", "1992-02-02", generateRandomEmailId(),
				"8005554242", "13 School St.", "Apt. 5", "Toronto", "ON", "B1B1B1", "Canada");

		System.out.println(contact);

		Object contactId = given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxOTgzNTh9.YK2QCt14E1mPelilpIarRUoevV7rcssNPILNiOw73Gc")
				.contentType(ContentType.JSON)
				// No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson
				// API must be included in pom.xml
				.body(contact) // Serialisation
				.when().post("/contacts").then().log().all().statusCode(201).and().statusLine("HTTP/1.1 201 Created")
				.extract().path("_id");

		System.out.println("User Id =" + contactId);

		System.out.println("==================================================");

		// 2. GET-Get Contact with created contactId -/users/contactId

		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxOTgzNTh9.YK2QCt14E1mPelilpIarRUoevV7rcssNPILNiOw73Gc")
				.when().get("/contacts/" + contactId).then().log().all().assertThat().statusCode(200).and()
				.body("_id", equalTo(contactId));// Correlation
	}

	@Test
	public void addContactTestWithLombokBuilder() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		// 1. POST - Create an user using POJO with Lombok(added in pom.xml)
		ContactLombak contact = new ContactLombak.ContactLombakBuilder().firstName("TestFN").lastName("TestLN")
				.birthdate("1989-09-09").email(generateRandomEmailId()).phone("9900089000").street1("13 School St")
				.street2("Apt 5").city("Toronto").stateProvince("ON").postalCode("L1L2L3").country("Canada").build();
		System.out.println("=====================================");
		System.out.println(contact);
		System.out.println("======================================");

		Object contactId = given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxOTgzNTh9.YK2QCt14E1mPelilpIarRUoevV7rcssNPILNiOw73Gc")
				.contentType(ContentType.JSON)
				// No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson
				// API must be included in pom.xml
				.body(contact) // Serialisation
				.when().post("/contacts").then().log().all().statusCode(201).and().statusLine("HTTP/1.1 201 Created")
				.extract().path("_id");

		System.out.println("Contact Id =" + contactId);

		System.out.println("==================================================");

		// 2. GET-Get Contact with created contactId -/users/contactId

		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxOTgzNTh9.YK2QCt14E1mPelilpIarRUoevV7rcssNPILNiOw73Gc")
				.when().get("/contacts/" + contactId).then().log().all().assertThat().statusCode(200).and()
				.body("_id", equalTo(contactId));// Correlation
	}
}
