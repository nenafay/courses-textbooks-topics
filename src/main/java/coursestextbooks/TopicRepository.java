package coursestextbooks;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {

	Topic findByName(String topicName);

	Topic findByName(Topic topicName);

	Topic findByNameIgnoreCaseLike(String topicName);

}
