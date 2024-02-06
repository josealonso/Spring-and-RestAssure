package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.model.Course;
import info.josealonso.usingRestAssured.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/courses")
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

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }

    @PostMapping(value = "/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(@RequestBody String code) {  // Not @RequestParam!!
        courseService.addCourse(code);
        return new Course(code);
    }

}
