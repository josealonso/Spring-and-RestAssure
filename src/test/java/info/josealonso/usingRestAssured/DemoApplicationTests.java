package info.josealonso.usingRestAssured;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public void initialiseMockMvcApplicationContext() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

	@Test
	public void givenNoMatchingCourseCodeWhenGetCourseThenRespondWithStatusNotFound() {
		String nonMatchingCourseCode = "nonMatchingCourseCode";

		given()
				.when()
				.get("/courses/" + nonMatchingCourseCode)
				.then()
				.log().ifValidationFails()
				.statusCode(NOT_FOUND.value());
	}

}
