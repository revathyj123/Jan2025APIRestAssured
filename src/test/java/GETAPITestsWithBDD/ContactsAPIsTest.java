package GETAPITestsWithBDD;

import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ContactsAPIsTest {
	
	
	@BeforeMethod //Executes before each method
	
	//Always method executes in Alphabetical order in TestNG
	public void setUp() {
		RestAssured.baseURI="https://thinking-tester-contact-list.herokuapp.com";
	}
	
	@Test(priority=1)
	public void getContactsAPITest() {

		//BDD
		//AAA-Arange-Act-Assert
		
		//Builder pattern
		given().log().all()
		.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDQ5OTAzNDF9.STHcX44IdaTHNI3POVRWMqJgk553VSTxifsmHO8znF8")
			.when()
				.get("/contacts")
					.then().log().all()
					.assertThat()
					.statusCode(200)
					.and()
					.contentType(ContentType.JSON);
	}
	
	@Test(priority=2)
	public void getContactsAPIAuthErrTest() {

		//BDD
		//AAA-Arange-Act-Assert
		
		//Builder pattern
		given().log().all()
		.header("Authorization", "Bearer Naveen")
			.when()
				.get("/contacts")
					.then().log().all()
					.assertThat()
					.statusCode(401);
	}
	
	@Test(priority=3)
	public void getContactsAPIAuthErrMsgTest() {

		//BDD
		//AAA-Arange-Act-Assert
		
		//Builder pattern
		String errMsg=given().log().all()
		.header("Authorization", "Bearer Naveen")
			.when()
				.get("/contacts")
					.then().log().all()
					.extract()
					.path("error");
		System.out.println("Error Message = "+errMsg);
		Assert.assertEquals(errMsg, "Please authenticate.");	
	}
}
