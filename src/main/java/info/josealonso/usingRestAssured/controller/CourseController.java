package info.josealonso.usingRestAssured.controller;

import info.josealonso.usingRestAssured.model.Course;
import info.josealonso.usingRestAssured.service.CourseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController   // @RestController = @Controller + @ResponseBody
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
    public ResponseEntity<Course> getCourse(@PathVariable String code) {
        return ResponseEntity.ok().body(courseService.getCourse(code));
    }

    @PostMapping(value = "/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> addCourse(@RequestBody String code) {  // Not @RequestParam!!
        Course newCourse = courseService.addCourse(code);
        HttpHeaders headers = new HttpHeaders(); // You can set the headers with ResponseEntity
        URI locationUri =
                UriComponentsBuilder.fromPath("/courses/{code}")
                        .path(String.valueOf(newCourse.code()))
                        .build()
                        .toUri();
        headers.setLocation(locationUri);
        return ResponseEntity.created(locationUri).body(new Course(code));
    }

}
