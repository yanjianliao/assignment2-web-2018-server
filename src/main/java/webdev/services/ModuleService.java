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
import webdev.models.Module;
import webdev.repositories.CourseRepository;
import webdev.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleService {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@GetMapping("api/course/{courseId}/module")
	public List<Module> findAllModulesForCourse(@PathVariable("courseId") int courseId) {
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			return course.getModules();
		}
		return new ArrayList<Module>();
	}
	
	@PostMapping("/api/course/{courseId}/module")
	public Module createModule(@PathVariable("courseId") int courseId, @RequestBody Module newModule) {
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			course.setModified(new Timestamp(System.currentTimeMillis()));
			newModule.setCourse(course);
			moduleRepository.save(newModule);
			return newModule;
		}
		return null;
	}	
	
	@DeleteMapping("/api/module/{moduleId}")
	public void deleteModule(@PathVariable("moduleId") int moduleId) {
		Optional<Module> data = moduleRepository.findById(moduleId);
		if(data.isPresent()) {
			Module module = data.get();
			Course course = module.getCourse();
			course.setModified(new Timestamp(System.currentTimeMillis()));
		}
			
		moduleRepository.deleteById(moduleId);
	}
		
	@GetMapping("/api/module")
	public List<Module> findAllModules() {
		
		return (List<Module>) moduleRepository.findAll();
	}
	
	@PutMapping("/api/module/{id}")
	public Module updateModule(@PathVariable("id") int id, @RequestBody Module module) {
		Optional<Module> data = moduleRepository.findById(id);
		if(data.isPresent()) {
			Module oldModule = data.get();
			oldModule.setCourse(module.getCourse());
			oldModule.setTitle(module.getTitle());
			moduleRepository.save(oldModule);
			return oldModule;
		}
		return null;
	}
	
	@GetMapping("/api/module/{id}")
	public Module findModuleById(@PathVariable("id") int id) {
		Optional<Module> data = moduleRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		
		return null;
	}
	
}
