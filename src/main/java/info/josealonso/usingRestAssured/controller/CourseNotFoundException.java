package info.josealonso.usingRestAssured.controller;

public class CourseNotFoundException extends RuntimeException {

    CourseNotFoundException(String code) {
        super(code);
    }
}
