package BookingAPITests;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class BookingAPITest {

	String tokenID; // global variable

	@BeforeMethod
	public void getToken() {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Credentials creds = new Credentials("admin", "password123");
		tokenID = given().log().all().contentType(ContentType.JSON).body(creds).when().post("/auth").then().assertThat()
				.statusCode(200).extract().path("token");

		System.out.println("Token = " + tokenID);
	}

	@Test
	public int createBooking() {

		BookingLombok.BookingDates bookingdates = new BookingLombok.BookingDates("2013-02-23", "2014-02-22");
		BookingLombok booking = new BookingLombok("Sally", "Brown", 111, true, bookingdates, "Breakfast");

		System.out.println(booking);

		int bookingid = given().log().all().contentType(ContentType.JSON).body(booking).when().post("/booking").then()
				.extract().path("bookingid");

		System.out.println("Booking ID = " + bookingid);
		return bookingid;

	}

	@Test
	public void createBookingTest() {
		int newBookingId = createBooking();
		Assert.assertNotNull(newBookingId);
	}

	@Test
	public void getBookingTest() {

		int bookingID = createBooking();

		given().log().all().when().get("/booking/" + bookingID).then().log().all().assertThat().statusCode(200).and()
				.body("firstname", equalTo("Sally"));
	}

	@Test
	public void updateBookingTest() {

		int newBookingId = createBooking();

		BookingLombok.BookingDates bookingdates = new BookingLombok.BookingDates("2013-02-23", "2014-02-22");
		
		BookingLombok booking = new BookingLombok("REvathy", "Ganesh Kumar", 111, true, bookingdates, "Dinner");
		
		given().log().all().pathParam("bookingId", newBookingId).contentType(ContentType.JSON)
				.header("Accept", "application/json").header("Cookie", "token=" + tokenID).body(booking).when()
				.put("/booking/{bookingId}").then().assertThat().statusCode(200).and()
				.body("additionalneeds", equalTo("Dinner"));

	}
	
	@Test
	public void deleteBookingTest() {
		int newBookingId = createBooking();
		given().log().all()
		.pathParam("bookingId", newBookingId)
		.contentType(ContentType.JSON)
		.header("Cookie", "token=" + tokenID)
		.when()
		.delete("/booking/{bookingId}")
		.then().log().all()
		.assertThat().statusCode(201);
	}

}
