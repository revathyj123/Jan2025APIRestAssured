package GETAPITestsWithBDD;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;


public class GetUserPostWithPathParam {
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{7840259, "Carpo aurum deinde volva molestiae sunt suggero talio."},
			{7840255, "Angelus quia omnis cursus argentum beneficium arceo umerus."},
			{7840257, "Basium qui nam tunc coadunatio conforto coepi arguo sortitus."}
		};
	}

	//Data Driven Approach

	@Test(dataProvider="getUserData")
	public void getUserPostTestWithPathParam(int userid, String title) {
		RestAssured.baseURI="https://gorest.co.in";

		given().log().all()
			.header("Authorization","Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
			.pathParam("userid", userid)
				.when()
					.get("/public/v2/users/{userid}/posts")
						.then().log().all()
							.assertThat()
								.statusCode(200)
								.and()
								.body("title",hasItem(title));
	}
	
	//Path and Query Param using HashMap
	//URL = "https://reqres.in"
	//Path Params = /api/users
	//Query Params=page=2
	
	@Test(dataProvider="getUserData")
	public void getUserPostTestWithPathParamUsingHashMap(int userid, String title) {
		RestAssured.baseURI="https://reqres.in";

		HashMap<String, String> pathParamMap=new HashMap<String, String>();
		pathParamMap.put("firstPath", "api");
		pathParamMap.put("secondPath","users");
		given().log().all()
			.pathParams(pathParamMap)
			.queryParam("page", 2)
				.when()
					.get("/{firstPath}/{secondPath}")
						.then().log().all()
							.assertThat()
								.statusCode(200);
	}
}
