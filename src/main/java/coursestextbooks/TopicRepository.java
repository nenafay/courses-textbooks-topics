package coursestextbooks;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {

	Topic findByName(String topicName);

	int findByName(Topic topicName);

}
