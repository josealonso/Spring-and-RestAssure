package info.josealonso.usingRestAssured;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CourseService {

    private static final Map<String, Course> COURSE_MAP = new ConcurrentHashMap<>();

    static {
        var Maths = new Course("Maths");
    }

    Collection<Course> getCourses() {
        return COURSE_MAP.values();
    }

    Course getCourse(String code) {
        return Optional.ofNullable(COURSE_MAP.get(code)).orElseThrow(
                () -> new CourseNotFoundException(code)
        );
    }
}
