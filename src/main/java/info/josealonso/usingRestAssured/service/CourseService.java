package info.josealonso.usingRestAssured.service;

import info.josealonso.usingRestAssured.model.Course;
import info.josealonso.usingRestAssured.controller.CourseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CourseService {

    private Set<Course> courseSet = new HashSet<>();

    public Set<Course> getAll() {
        if (courseSet.isEmpty())
            return Collections.emptySet();
        else
            return courseSet;
    }

    public Course getCourse(String code) {
        return courseSet.stream()
                        .filter(course -> course.code()
                                .equals(code))
                                .findFirst()
                                .orElseThrow(() -> new CourseNotFoundException(code)
        );
    }
}
