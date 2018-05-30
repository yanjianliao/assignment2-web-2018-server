package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Topic;

public interface TopicRepository  extends CrudRepository<Topic, Integer> {

}
