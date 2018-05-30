package webdev.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Course;
import webdev.repositories.CourseRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseServices {

	@Autowired
	CourseRepository courseRepository;
	@GetMapping("/api/course")
	public List<Course> findAllCourses() {
		return (List<Course>) courseRepository.findAll();
	}
	
	@PostMapping("/api/course")
	public Course createCourse(@RequestBody Course course) {
		course.setCreated(new Timestamp(System.currentTimeMillis()));
		courseRepository.save(course);
		
		return course;
	}
	
	@DeleteMapping("/api/course/{courseId}")
	public void deleteCourseById(@PathVariable("courseId") int courseId) {
		courseRepository.deleteById(courseId);
	}
	
	@GetMapping("/api/course/{courseId}")
	public Course findCourseById(@PathVariable("courseId") int courseId) {
		
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			return course;
		}
		return null;
	}
	
	@PutMapping("/api/course")
	public Course updateCourseById(@RequestBody Course course) {	
		Course oldCourse = findCourseById(course.getId());
		if(oldCourse == null) 
			return null;
		oldCourse.setTitle(course.getTitle());
		oldCourse.setModified(new Timestamp(System.currentTimeMillis()));
		courseRepository.save(oldCourse);
		return oldCourse;	
	}
	
	
}
