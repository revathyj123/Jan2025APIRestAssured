package GetCallWithDesrialisation;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAUserTest {

	@Test
	public void getAUserTest() {

		RestAssured.baseURI = "https://gorest.co.in";

		Response response = given().log().all()
				.header("Authorization", "Bearer 0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b")
				.when().get("/public/v2/users/7848265");

		// Deserialisation - JSON to POJO
		String responseBody = response.asString();
		ObjectMapper mapper = new ObjectMapper();

		try {
			//User userRes = mapper.readValue(responseBody, User.class);---For POJO
			UserLombok userRes = mapper.readValue(responseBody, UserLombok.class);//---For Deserialisation
			System.out.println(userRes);
			
			Assert.assertEquals(userRes.getName(), "Chapala Dwivedi");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
