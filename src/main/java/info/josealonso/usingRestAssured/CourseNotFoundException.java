package info.josealonso.usingRestAssured;

public class CourseNotFoundException extends RuntimeException {

    CourseNotFoundException(String code) {
        super(code);
    }
}
