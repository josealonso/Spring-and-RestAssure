package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.Course;
import info.josealonso.usingRestAssured.service.CourseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public Object getCourses() {
        var courses = courseService.getAll();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }

}
