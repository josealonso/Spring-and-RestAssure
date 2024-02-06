package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.model.Course;
import info.josealonso.usingRestAssured.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Course>> getCourses() {
        var courses = courseService.getAll();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }

    @PostMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(@RequestBody String code) {
        courseService.addCourse(code);
        return new Course(code);
    }

}
