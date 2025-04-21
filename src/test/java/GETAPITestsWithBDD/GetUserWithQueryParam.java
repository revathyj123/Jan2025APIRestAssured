package GETAPITestsWithBDD;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GetUserWithQueryParam {
	@Test
	public void getSingleUserTestWithQueryParam() {
		RestAssured.baseURI="https://gorest.co.in";
		
		given().log().all()
			.header("Authorization","0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.queryParam("name", "Naveen")
				.queryParam("status", "active")
					.when()
						.get("/public/v2/users")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON);							
	}
	//Using HashMap
	@Test
	public void getUsersTestWithQueryParamUsingHashMap() {
		RestAssured.baseURI="https://gorest.co.in";
		
		HashMap<String, String> userQueryMap=new HashMap<String, String>();
		userQueryMap.put("name", "Naveen");
		userQueryMap.put("status","inactive");
		userQueryMap.put("gender", "male");
		given().log().all()
			.header("Authorization","0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.queryParams(userQueryMap)
					.when()
						.get("/public/v2/users")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON);							
	}
}
