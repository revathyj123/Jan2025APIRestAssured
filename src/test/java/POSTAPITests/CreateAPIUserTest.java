package POSTAPITests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//POST-GoRest-with hard coded Request Body and Json data file

public class CreateAPIUserTest {
	
	public String generateRandomEmailId() {
		String emailId="testapi_"+System.currentTimeMillis()+"@testemail.com";
		return emailId;
	}
	
	@Test
	public void createUserWithJSONStringTest() {
		RestAssured.baseURI="https://gorest.co.in";
		
		String emailId=generateRandomEmailId();
		
		given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
			.contentType(ContentType.JSON)
				.body("{\r\n"
					+ "    \"name\": \"Varma\",\r\n"
					+ "    \"gender\": \"male\",\r\n"
					+ "    \"email\": \""+emailId+"\",\r\n"
					+ "    \"status\": \"active\"\r\n"
					+ "}")
		.when()
			.post("/public/v2/users")
		.then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.statusLine("HTTP/1.1 201 Created");
	}
	
	@Test
	public void createUserWithJSONFileTest() {
		RestAssured.baseURI="https://gorest.co.in";
		
		given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
			.contentType(ContentType.JSON)
				.body(new File("./src/test/resources/jsons/user.json"))
		.when()
			.post("/public/v2/users")
		.then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.statusLine("HTTP/1.1 201 Created");
	}
	
	//Replacing email in user.json with random email generated
	@Test
	public void createUserWithJSONFileEmailReplacementTest() throws IOException{
		RestAssured.baseURI="https://gorest.co.in";
		
		//Convert the user.json to String
		String emailId=generateRandomEmailId();
		String rawJson=new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
		String updatedJson=rawJson.replace("{{email}}", emailId);
		
		Object userId=given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
			.contentType(ContentType.JSON)
				.body(updatedJson)
		.when()
			.post("/public/v2/users")
		.then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.statusLine("HTTP/1.1 201 Created")
				.extract()
				.path("id");
		System.out.println("User Id ="+userId);
	}
	
}


