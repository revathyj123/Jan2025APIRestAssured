package PUTCallWithBDD;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import CreateWithPOJOAndLombok.CreateUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class UpdateUserTest {

	public String generateRandomEmailId() {
		String emailId = "testapi_" + System.currentTimeMillis() + "@testemail.com";
		return emailId;
	}

	@Test
	public void updateSingleUserTest() {

		// 1. POST
		RestAssured.baseURI = "https://gorest.co.in";

		// 1. POST - Create an user using POJO with Jackson library(added in pom.xml)
		CreateUser user1 = new CreateUser("Revathy", "female", generateRandomEmailId(), "active");

		Object userId = given().log().all()
				.header("Authorization", "Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.contentType(ContentType.JSON)
				// No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson
				// API must be included in pom.xml
				.body(user1) // Serialisation
				.when().post("/public/v2/users").then().log().all().statusCode(201).and()
				.statusLine("HTTP/1.1 201 Created").extract().path("id");

		System.out.println("User Id =" + userId);

		System.out.println("==================================================");

		// 2. GET-GetUser with created UserId -/users/userId

		given().log().all()
				.header("Authorization", "Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.when().get("/public/v2/users/" + userId).then().log().all().assertThat().statusCode(200).and()
				.body("id", equalTo(userId));// Correlation

		System.out.println("==================================================");

		// 3. PUT
		user1.setName("Revathy Automation");
		user1.setStatus("inactive");

		given().log().all()
				.header("Authorization", "Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.contentType(ContentType.JSON).body(user1).when().put("/public/v2/users/" + userId).then().log().all()
				.assertThat().statusCode(200).and().body("id", equalTo(userId))// Correlation
				.body("name", equalTo(user1.getName()));

		System.out.println("==================================================");

		// 4. GET

		given().log().all()
				.header("Authorization", "Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.when().get("/public/v2/users/" + userId).then().log().all().assertThat().statusCode(200).and()
				.body("id", equalTo(userId))
				.body("name", equalTo(user1.getName()));
	}

}
