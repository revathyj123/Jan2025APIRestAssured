package CreateWithPOJOAndLombok;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateUserTestWithPOJO {
	
	public String generateRandomEmailId() {
		String emailId="testapi_"+System.currentTimeMillis()+"@testemail.com";
		return emailId;
	}
	
//	public static void main(String args[]) {
//		
//		CreateUser u1=new CreateUser("Revathy", "female", "emailId@gmail.com", "active");
//		System.out.println(u1);
//	}
	
	@Test
	public void addUserTest() {
		RestAssured.baseURI="https://gorest.co.in";
		
		//1. POST - Create an user using POJO with Jackson library(added in pom.xml)
		CreateUser user1=new CreateUser("Tom", "male", generateRandomEmailId(), "active");
		
		Object userId=given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
			.contentType(ContentType.JSON)
			//No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson API must be included in pom.xml
			.body(user1) //Serialisation 
		.when()
			.post("/public/v2/users")
		.then().log().all()
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
