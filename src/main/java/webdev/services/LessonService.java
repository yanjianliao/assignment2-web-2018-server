package webdev.services;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import webdev.models.Lesson;
import webdev.models.Module;
import webdev.repositories.CourseRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonService {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	
	@GetMapping("/api/lesson")
	public List<Lesson> findAllLessons() {
		return (List<Lesson>)lessonRepository.findAll();
	}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public List<Lesson> findAllLessonsForModule(@PathVariable("moduleId") int moduleId) {
		Optional<Module> data = moduleRepository.findById(moduleId);
		if(data.isPresent()) {
			Module module = data.get();
			return module.getLessons();
		}
		
		return new ArrayList<Lesson>();
	}
	
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public Lesson createLesson(@PathVariable("moduleId") int moduleId, @RequestBody Lesson lesson) {
		Optional<Module> data = moduleRepository.findById(moduleId);
		if(data.isPresent()) {
			Module module = data.get();
			Course course = module.getCourse();
			course.setModified(new Timestamp(System.currentTimeMillis()));
			lesson.setModule(module);	
//			System.out.println(lesson.getId());
			lessonRepository.save(lesson);
		}
		
		return lesson;
	}
	
	@DeleteMapping("/api/lesson/{id}")
	public void deleteLesson(@PathVariable int id) {
		Optional<Lesson> data = lessonRepository.findById(id);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			Module module = lesson.getModule();
			Course course = module.getCourse();
			course.setModified(new Timestamp(System.currentTimeMillis()));
		}
		lessonRepository.deleteById(id);
	}
	
	@GetMapping("/api/lesson/{id}")
	public Lesson findLessonById(@PathVariable("id")int id) {
		Optional<Lesson> data = lessonRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	
	@PutMapping("/api/lesson/{id}")
	public Lesson updateLesson(@PathVariable("id") int id, @RequestBody Lesson lesson) {
		Lesson oldLesson = findLessonById(id);
		if(oldLesson == null)
			return lesson;
		oldLesson.setTitle(lesson.getTitle());
		lessonRepository.save(oldLesson);
		return oldLesson;
	}
}
