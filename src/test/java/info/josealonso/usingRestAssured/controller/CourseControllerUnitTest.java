package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.service.CourseService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class CourseControllerUnitTest {

    @Mock
    private CourseService courseService;
    @InjectMocks
    private CourseController courseController;
    @InjectMocks
    private CourseControllerExceptionHandler courseControllerExceptionHandler;

    @BeforeAll
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(courseController, courseControllerExceptionHandler);
    }

    /*
      REST-assured uses the familiar given-when-then scenario format to define the test:
        given() - specifies the HTTP request details.
        when() - specifies the HTTP verb as well as the route.
        then() - validates the HTTP response.
     */

    @Test
    public void givenNoExistingCoursesWhenGetCoursesThenResponseWithStatusOkAndEmptyArray() {
        when(courseService.getCourses()).thenReturn(Collections.emptyList());

        given()
                .when()
                .get("/courses")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(ContentType.JSON)
                .body(is(equalTo("[]")));
    }

    @Test
    public void givenNoMatchingCoursesWhenGetCoursesThenResponseWithStatusNotFound() {
        String nonMatchingCourseCode = "nonMatchingCourseCode";

        when(courseService.getCourse(nonMatchingCourseCode)).thenThrow(
                new CourseNotFoundException(nonMatchingCourseCode)
        );

        given()
                .when()
                .get("/courses" + nonMatchingCourseCode)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());
    }

}
