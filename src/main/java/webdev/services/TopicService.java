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
import webdev.models.Topic;
import webdev.repositories.CourseRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.ModuleRepository;
import webdev.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TopicService {
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	TopicRepository topicRepository;
	
	@GetMapping("/api/topics")
	public List<Topic> findAllTopics() {
		return (List<Topic>)topicRepository.findAll();
	}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public List<Topic> findAllTopicsForLesson(@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if(data.isPresent()) {
			Lesson lesson = data.get();
			return lesson.getTopics();
		}
		return new ArrayList<>();
	}
	
	@GetMapping("/api/topic/{id}")
	public Topic findTopicById(@PathVariable("id") int id) {
		Optional<Topic> data = topicRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public Topic createTopic(@RequestBody Topic topic, @PathVariable("lessonId") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		
		if(data.isPresent()) {
			Lesson lesson = data.get();
			lesson.getModule()
			.getCourse()
			.setModified(new Timestamp(System.currentTimeMillis()));
			topic.setLesson(lesson);
			topicRepository.save(topic);
		}
		return topic;
	}
  	
	
	
}
