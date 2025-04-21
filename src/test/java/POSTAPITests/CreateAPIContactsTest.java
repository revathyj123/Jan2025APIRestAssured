package POSTAPITests;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//POST-Contacts API https://thinking-tester-contact-list.herokuapp.com/-with hard coded Request Body and Json data file
public class CreateAPIContactsTest {
	
	public String generateRandomEmailId() {
		String emailId="testapi_"+System.currentTimeMillis()+"@testemail.com";
		return emailId;
	}
	
	@Test
	public void createContactWithJSONStringTest() {
		RestAssured.baseURI="https://thinking-tester-contact-list.herokuapp.com";
		
		String emailId= generateRandomEmailId();
		given().log().all()
			.header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxNjQ1MzZ9.MaAfYjz42x6w9Dwkf-9di5-6tZVbD4ZPjwP2kGO1qYU")
			.contentType(ContentType.JSON)
				.body("{\r\n"
						+ "    \"firstName\": \"Revathy\",\r\n"
						+ "    \"lastName\": \"Ganesh\",\r\n"
						+ "    \"birthdate\": \"1989-02-02\",\r\n"
						+ "    \"email\": \""+emailId+"\",\r\n"
						+ "    \"phone\": \"9900099000\",\r\n"
						+ "    \"street1\": \"13 School St.\",\r\n"
						+ "    \"street2\": \"Apt. 5\",\r\n"
						+ "    \"city\": \"Mississauga\",\r\n"
						+ "    \"stateProvince\": \"ON\",\r\n"
						+ "    \"postalCode\": \"A1A1A1\",\r\n"
						+ "    \"country\": \"Canada\"\r\n"
						+ "}")
		.when()
			.post("/contacts")
		.then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.statusLine("HTTP/1.1 201 Created");
	}
	
	@Test
	public void createContactsWithJSONFileTest() {
		RestAssured.baseURI="https://thinking-tester-contact-list.herokuapp.com";
		
		given().log().all()
			.header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxNjQ1MzZ9.MaAfYjz42x6w9Dwkf-9di5-6tZVbD4ZPjwP2kGO1qYU")
			.contentType(ContentType.JSON)
				.body(new File("./src/test/resources/jsons/contacts.json"))
		.when()
			.post("/contacts")
		.then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.statusLine("HTTP/1.1 201 Created");
	}
	
	@Test
	public void createContactsWithJSONFileWithStringReplacementTest() throws IOException {
		RestAssured.baseURI="https://thinking-tester-contact-list.herokuapp.com";
		
		String emailId=generateRandomEmailId();
		String rawJson=new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/contacts.json")));
		String updatedJson=rawJson.replace("{{email}}", emailId);
		
		Object contactsId=given().log().all()
								.header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUxNjQ1MzZ9.MaAfYjz42x6w9Dwkf-9di5-6tZVbD4ZPjwP2kGO1qYU")
								.contentType(ContentType.JSON)
								.body(updatedJson)
							.when()
								.post("/contacts")
							.then().log().all()
								.assertThat()
								.statusCode(201)
								.and()
								.statusLine("HTTP/1.1 201 Created")
								.extract()
								.path("_id");
		
		System.out.println("Contacts Id = "+contactsId);
	}
	
	//POJO - Java Object - Serialisation - Deserialisation ---> Jackson/Lombok api + builder Patterns
	
	
}
