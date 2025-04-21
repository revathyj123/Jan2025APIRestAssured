package POSTApiwithDifferentBodyType;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostwithDiffBodyTypes {
	
	@Test
	public void bodyWithTextTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		String payLoad="Hi This is Revathy here";
		
		given().log().all()
			.contentType(ContentType.TEXT)
			.body(payLoad)
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void bodyWithJavaScriptTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		String payLoad="<script>\r\n"
				+ "document.getElementById(\"demo\").innerHTML =\r\n"
				+ "\"Number of anchors are: \" + document.anchors.length;\r\n"
				+ "</script>";
		
		given().log().all()
			.contentType("application/javascript;charset=utf-8")
			.body(payLoad)
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void bodyWithHTMLTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		String payLoad="<html>\r\n"
				+ "<body>\r\n"
				+ "<p id=\"demo\">JavaScript can change HTML content.</p>\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
		given().log().all()
			.contentType(ContentType.HTML)
			.body(payLoad)
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void bodyWithXMLTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		String payLoad="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "- <note>\r\n"
				+ "  <to>Tove</to>\r\n"
				+ "  <from>Jani</from>\r\n"
				+ "  <heading>Reminder</heading>\r\n"
				+ "  <body>Don't forget me this weekend!</body>\r\n"
				+ "</note>";
		
		given().log().all()
			.contentType("application/xml;charset=utf-8")
			.body(payLoad)
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void bodyWithMultiPartTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		given().log().all()
			.contentType(ContentType.MULTIPART)
			.multiPart("resume", new File("C:/Users/ganes/OneDrive/Desktop/SDETAPIAutomationResume.pdf"))
			.multiPart("name", "Revathy")
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}

	@Test
	public void bodyWithBinaryTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		given().log().all()
			.contentType("application/pdf")
			.body(new File("C:/Users/ganes/OneDrive/Desktop/SDETAPIAutomationResume.pdf"))
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void bodyWithImageTest() {
		RestAssured.baseURI="https://postman-echo.com";
		
		given().log().all()
			.contentType("image/jpeg")
			.body(new File("C:\\Users\\ganes\\OneDrive\\Desktop\\pineTreeImage.jpg"))
		.when()
			.post("/post")
		.then().log().all()
			.assertThat()
			.statusCode(200);
	}
}