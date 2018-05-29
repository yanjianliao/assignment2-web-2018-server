package webdev.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Lesson;
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
	
}
