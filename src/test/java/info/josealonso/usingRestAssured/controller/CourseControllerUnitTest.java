package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.service.CourseService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

//import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

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
    }

    /*
      REST-assured uses the familiar given-when-then scenario format to define the test:
        given() - specifies the HTTP request details.
        when() - specifies the HTTP verb as well as the route.
        then() - validates the HTTP response.
     */

    @Test
    public void givenNoExistingCoursesWhenGetCoursesThenResponseWithStatusOkAndEmptyArray() {

        when(courseService.getAll()).thenReturn(Collections.emptySet());

        get("/courses").then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(is(equalTo("[]")));
    }

    @Test
    public void givenNoMatchingCoursesWhenGetCoursesThenResponseWithStatusNotFound() {
        String nonMatchingCourseCode = "nonMatchingCourseCode";

        when(courseService.getCourse(nonMatchingCourseCode)).thenThrow(
                new CourseNotFoundException(nonMatchingCourseCode)
        );

        get("/courses" + nonMatchingCourseCode)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
