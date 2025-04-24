package CreateWithPOJOAndLombok;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateAnUserWithLombokTest {
	
	
	public String generateRandomEmailId() {
		String emailId="testapi_"+System.currentTimeMillis()+"@testemail.com";
		return emailId;
	}
	
	@Test
	public void addUserTestWithLombok() {
		RestAssured.baseURI="https://gorest.co.in";

		UserLombok user =new UserLombok("TestUser","female", generateRandomEmailId(), "active");
		
		System.out.println(user);
		
		//1. POST - Create an user using POJO with Jackson library(added in pom.xml)

		Object userId=given().log().all()
				.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.contentType(ContentType.JSON)
				//No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson API must be included in pom.xml
				.body(user) //Serialisation 
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
	
	//Builder Pattern
	@Test
	public void addUserTestWithLombokBuilder() {
		
		RestAssured.baseURI="https://gorest.co.in";
		
		//builder pattern using Lombok
		UserLombok user=new UserLombok.UserLombokBuilder()
					.name("TestUser")
					.gender("female")
					.email(generateRandomEmailId())
					.status("active")
					.build();
		
		System.out.println(user);
		
		//1. POST - Create an user using POJO with Jackson library(added in pom.xml)

		Object userId=given().log().all()
				.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.contentType(ContentType.JSON)
				//No JSON serialiser found in classpath - IllegalStateException -FIX: Jackson API must be included in pom.xml
				.body(user) //Serialisation 
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
	
	@Test
	public void addUserTestWithLombokBuilderGetter() {
		
		RestAssured.baseURI="https://gorest.co.in";
		
		//builder pattern using Lombok
		UserLombok user=new UserLombok.UserLombokBuilder()
					.name("TestUser")
					.gender("female")
					.email(generateRandomEmailId())
					.status("active")
					.build();
		
		System.out.println(user);
		//To test Username with getNAme() using Lombok Getter
		Response response=given().log().all()
				.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.contentType(ContentType.JSON)
				.body(user) //Serialisation 
			.when()
				.post("/public/v2/users");
//			.then().log().all()
//				.statusCode(201)
//				.and()
//				.statusLine("HTTP/1.1 201 Created")
//				.extract()
//				.path("name");
		
//		System.out.println("User Name ="+userName);
				
			JsonPath js=response.jsonPath();
		
		Assert.assertEquals(js.getString("name"), user.getName());
		Assert.assertEquals(js.getString("gender"), user.getGender());
		Assert.assertEquals(js.getString("email"), user.getEmail());
		Assert.assertEquals(js.getString("status"), user.getStatus());
		Assert.assertNotNull(js.getString("id"));
	}
	
}
