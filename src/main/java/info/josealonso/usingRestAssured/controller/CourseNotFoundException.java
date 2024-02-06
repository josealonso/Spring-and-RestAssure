package info.josealonso.usingRestAssured.controller;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String code) {
        super(code);
    }
}
