package GETAPITestsWithNonBDD;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GETContactsAPITest {
	
	@Test
	public void getContactsTest() {
		RestAssured.baseURI="https://thinking-tester-contact-list.herokuapp.com";
		RequestSpecification request = RestAssured.given();//cannot use .log().all() as its non-BDD approach
		
		request.header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FiYjczODM5ZTY5MzAwMTNkNTFjN2YiLCJpYXQiOjE3NDUwOTQ3OTN9.XTQP3DGIT2Vq7wIdqKUucw93WlMjpM4ekDElxQ-d4Cs");
		Response response=request.get("/contacts");
		System.out.println(response.statusCode());
		System.out.println(response.statusLine());	
		//response.prettyPrint();
		String contentType=response.header("content-type");
		System.out.println(contentType);
		
		Headers headers = response.headers();
		
		List<Header> headersList = headers.asList();
		System.out.println("Headers Size= "+headersList.size());
		
		for(Header e:headersList) {
			String name=e.getName();
			String value=e.getValue();
			System.out.println(name+" : "+value);
		}
			
	}
	
}
