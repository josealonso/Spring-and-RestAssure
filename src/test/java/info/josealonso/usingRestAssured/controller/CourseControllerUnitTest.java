package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.service.CourseService;
import io.restassured.module.webtestclient.RestAssuredWebTestClient.*;
import io.restassured.module.webtestclient.matcher.RestAssuredWebTestClientMatchers.*;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseControllerUnitTest {

    @Mock
    private CourseService courseService;
    @InjectMocks
    private CourseController courseController;
    @InjectMocks
    private CourseControllerExceptionHandler courseControllerExceptionHandler;

    @BeforeEach
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(courseController, courseControllerExceptionHandler);
        courseService.clearSet();
    }

    /*
      REST-assured uses the familiar given-when-then scenario format to define the test:
        given() - specifies the HTTP request details.
        when() - specifies the HTTP verb as well as the route.
        then() - validates the HTTP response.
     */

    @Test
    public void givenNoExistingCoursesWhenGetCoursesThenResponseWithStatusOkAndEmptyArray() {

        given()
                .when()
                .get("/courses").then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(is(equalTo("[]")));
    }

    @Test
    public void givenNoMatchingCoursesWhenGetCoursesThenResponseWithStatusNotFound() {
        String nonMatchingCourseCode = "nonMatchingCourseCode";

        get("/courses" + nonMatchingCourseCode)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    public  String getJsonPath(Response response, String key) {  // Not used
        var complete = response.asString();
        JsonPath js = new JsonPath(complete);
        return js.get(key).toString();
    }

    @Test
    public void givenANewCourseWhenAddCourseThenResponseWithStatusOkAndTheNewCourse() {
        String validCourseCode = "CourseCode";

        Response response =
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(validCourseCode)
                .when()
                .post("/courses/" + validCourseCode)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
        Assertions.assertEquals(response.path("code"), validCourseCode);     // IT WORKS!!

        System.out.println("===============================");
        System.out.println(response.asPrettyString());
    }

}
