package GETAPITestsWithBDD;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GoRestAPITest {
	
	@Test
	public void getSingleUserTest() {
		RestAssured.baseURI="https://gorest.co.in";
		
		Response response=given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.when()
					.get("/public/v2/users/7839092");
		
		System.out.println("StatusCode="+response.statusCode());
		System.out.println("StatusLine="+response.statusLine());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("200 OK"));
		
		response.prettyPrint();
		
		JsonPath jPath = response.jsonPath();
		
		int userId=jPath.getInt("id");
		System.out.println("User ID = "+userId);
		Assert.assertEquals(userId, 7839092);
		
		
		String name=jPath.getString("name");
		System.out.println("Name of the User = "+name);
		Assert.assertEquals(name, "Deevakar Gupta II");
		
		String status = jPath.getString("status");
		System.out.println("Status = "+status);
		Assert.assertEquals(status, "active");
	}
	
	@Test
	public void getAllUsersTest() {
		RestAssured.baseURI="https://gorest.co.in";
		
		Response response=given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.when()
					.get("/public/v2/users");
		
		System.out.println("StatusCode="+response.statusCode());
		System.out.println("StatusLine="+response.statusLine());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("200 OK"));
		
		response.prettyPrint();
		
		JsonPath jPath = response.jsonPath();
		
		List<Integer> idList = jPath.getList("id");
		System.out.println(idList);
		
		List<String> nameList = jPath.getList("name");
		System.out.println(nameList);
	}
	

}
