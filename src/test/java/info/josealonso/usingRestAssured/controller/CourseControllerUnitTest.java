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
import static org.springframework.http.HttpStatus.OK;

//import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

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
}
