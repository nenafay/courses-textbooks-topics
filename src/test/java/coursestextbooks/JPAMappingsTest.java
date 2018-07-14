package coursestextbooks;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest

public class JPAMappingsTest {
	
	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private TopicRepository topicRepo;
	
	@Resource
	private CourseRepository courseRepo;
	
	@Resource
	private TextbookRepository textbookRepo;
	
	@Test
	public void shouldSaveAndLoadTopic() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		
		entityManager.flush();//forces JPA to hit database when we try to find it.
		entityManager.clear();
		
		Optional<Topic>result = topicRepo.findById(topicId);
		topic = result.get();
		assertThat(topic.getName(), is("topic"));
	}
	
	@Test
	public void shouldGenerateTopicId() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		
		entityManager.flush();	
		entityManager.clear();
		
		assertThat(topicId, is (greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadCourse() {
		Course course = courseRepo.save(new Course("course", "description"));
		long courseId = course.getId();

		entityManager.flush();
		entityManager.clear();
		
		Optional<Course>result = courseRepo.findById(courseId);
		course = result.get();
		assertThat(course.getName(), is("course"));
		//OR Course course = new Course ("course", "description);
		//   Course = courseRepo.save(course);
		//then as above.
	}
	@Test
	public void shouldEstablishCourseToTopicsRelationships() {
		//topic is not the owner so we create these first.
		Topic java = topicRepo.save(new Topic ("Java"));
		Topic ruby = topicRepo.save(new Topic ("Ruby"));
		
		Course course = new Course("00 Languages", "description", java, ruby);
		course = courseRepo.save(course);
		long courseId = course.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		
		assertThat(course.getTopics(), containsInAnyOrder(java, ruby));
		
	}
	
	@Test//test query
	public void shouldFindCoursesForTopic() {
		Topic java = topicRepo.save(new Topic("java"));
		
		Course ooLanguages = courseRepo.save(new Course("00 Languages", "description", java));
		Course advancedJava = courseRepo.save(new Course("advancedJava", "description", java));
		
		entityManager.flush();
		entityManager.clear();
		//findByTopics refers to topics 
		Collection<Course>coursesForTopic = courseRepo.findByTopicsContains(java);
		
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguages, advancedJava));
	}
	
	@Test//test query
	public void shouldFindCoursesForTopicId() {
		//many courses will use many topics
		Topic ruby = topicRepo.save(new Topic ("ruby"));
		long topicId = ruby.getId();
		
		Course ooLanguages = courseRepo.save(new Course("00 Languages", "description", ruby));
		Course advancedRuby = courseRepo.save(new Course("advancedJava", "description", ruby));
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Course> coursesForTopic = courseRepo.findByTopicsId(topicId);
		
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguages, advancedRuby));
	}
	
	@Test
	public void shouldEstablishTextBookToCourseRelationship() {	
		//many courses will use one book
		Course course = new Course("name", "description");
		//don't need to specify topics bc not involved in this relationship.
		courseRepo.save(course);
		long courseId = course.getId();
		
		Textbook book = new Textbook("title", course);
		textbookRepo.save(book);
		
		Textbook book2 = new Textbook("title", course);
		textbookRepo.save(book2);
	
		entityManager.flush();
		entityManager.clear();
		
		Optional<Course>result = courseRepo.findById(courseId);
		course = result.get();
		assertThat(course.getTextbooks(), containsInAnyOrder(book,book2));
		
	}
	
	@Test
	public void shouldSortCourses() {
		Course ooLanguages = new Course ("OO Languages", "description");
		ooLanguages = courseRepo.save(ooLanguages);
	
		Course scriptingLanguages = new Course("Scripting Languages", "description");
		scriptingLanguages = courseRepo.save(scriptingLanguages);
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Course> sortedCourses = courseRepo.findAllByOrderByNameAsc();
		assertThat(sortedCourses, contains(ooLanguages, scriptingLanguages));
	}
}
