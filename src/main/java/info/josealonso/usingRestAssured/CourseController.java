package info.josealonso.usingRestAssured;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }

}
