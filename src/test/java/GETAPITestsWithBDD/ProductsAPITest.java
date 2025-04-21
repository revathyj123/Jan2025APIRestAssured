package GETAPITestsWithBDD;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

//List/Array of products using List
//URL="https://fakestoreapi.com"

public class ProductsAPITest {

	@Test
	public void getProductsTest() {
		RestAssured.baseURI="https://fakestoreapi.com";
		
		Response response = given().log().all()
							.when()
								.get("/products");
		System.out.println(response.statusCode());
		System.out.println(response.statusLine());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("200 OK"));
		
		response.prettyPrint();
		
		JsonPath js=response.jsonPath();
		
		List<Integer> ids=js.getList("id");
		System.out.println("List of Integers ="+ids);
		
		List<Object> priceList=js.getList("price");
		System.out.println("List of price ="+priceList);
		
		List<Object> rateList=js.getList("rating.rate");
		System.out.println("List of rate ="+rateList);

		List<Integer> countList=js.getList("rating.count");
		System.out.println("List of count ="+countList);
		
		for(int i=0;i<ids.size();i++) {
			int id=ids.get(i);
			Object price=priceList.get(i);
			Object rate = rateList.get(i);
			int count=countList.get(i);
			
			System.out.println("Id = "+id+" Price = "+price+" Rate = "+ rate+" Count = "+count);

		}
		
		
	}
	
	//To count the no.of products in response
	@Test
	public void getProductsCountTest() {
		RestAssured.baseURI="https://fakestoreapi.com";
		
		given().log().all()
			.when()//As we dont have anything in given() and when() we can start from .get() too
				.get("/products")
					.then()
						.assertThat()
							.statusCode(200)
								.and()
									.body("$.size()", equalTo(20));
	}			
}