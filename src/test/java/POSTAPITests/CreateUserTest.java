package POSTAPITests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateUserTest {

	//1. POST-Create a User --> get UserId
	//2. GET-GetUser with UserId
	
	public String generateRandomEmailId() {
		String emailId="testapi_"+System.currentTimeMillis()+"@testemail.com";
		return emailId;
	}
	
	@Test
	public void createUserWithJSONFileEmailReplacementTest() throws IOException{
		RestAssured.baseURI="https://gorest.co.in";
		
		//Convert the user.json to String
		String emailId=generateRandomEmailId();
		String rawJson=new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
		String updatedJson=rawJson.replace("{{email}}", emailId);
		
		//1. POST-Create a User --> get UserId
		
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
		
		System.out.println("==================================================");
		
		//2. GET-GetUser with created UserId -/users/userId
		
		given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
		.when()
			.get("/public/v2/users/"+userId)
		.then().log().all()
			.assertThat()
			.statusCode(200)
			.and()
			.body("id",equalTo(userId));//Correlation
	}
	
}
