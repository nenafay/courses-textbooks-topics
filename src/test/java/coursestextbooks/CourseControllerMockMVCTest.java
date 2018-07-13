package coursestextbooks;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerMockMVCTest {

	@Resource
	private MockMvc mvc;
	
	@Mock
	private Course course;
	
	@Mock 
	private Course anotherCourse;
	
	@Mock
	private Topic topic;
	
	@Mock
	private Topic anotherTopic;
	
	@MockBean
	private CourseRepository courseRepo;
	
	@MockBean
	private TopicRepository topicRepo;
	
	@Test
	public void shouldRouteToSingleCourseView() throws Exception {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(view().name(is("course")));
	}
	
	@Test
	public void shouldBeOkForSingleCourse() throws Exception {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(status().isOk());
	}
	
	@Test 
	public void shouldNotBeOkForSingleCourse() throws Exception {
		mvc.perform(get("/course?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleCourseIntoModel() throws Exception {
		when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
		
		mvc.perform(get("/course?id=1")).andExpect(model().attribute("courses", is(course)));
	}
	
	@Test
	public void shouldRouteToAllCoursesView() throws Exception {
		mvc.perform(get("/show-courses")).andExpect(view().name(is("courses")));
	}
	
	@Test
	public void shouldBeOkForAllCourses() throws Exception {
		mvc.perform(get("/show-courses")).andExpect(status().isOk());	
	}
	
	@Test
	public void shouldPutAllCoursesIntoModel() throws Exception {
		Collection<Course>allCourses = Arrays.asList(course, anotherCourse);
		when(courseRepo.findAll()).thenReturn(allCourses);
		
		mvc.perform(get("/show-courses")).andExpect(model().attribute("courses",  is(allCourses)));
	}
	@Test
	public void shouldRouteToSingleTopicView() throws Exception {
		long arbitraryTopicId = 42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(view().name(is("topic")));
	}
	
	@Test
	public void shouldBeOkForSingleTopic() throws Exception {
		long arbitraryTopicId = 42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(status().isOk());
	}
	
	@Test 
	public void shouldNotBeOkForSingleTopic() throws Exception {
		mvc.perform(get("/topic?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleTopicIntoModel() throws Exception {
		when(topicRepo.findById(1L)).thenReturn(Optional.of(topic));
		
		mvc.perform(get("/topic?id=1")).andExpect(model().attribute("topics", is(topic)));
	}
	
	@Test
	public void shouldRouteToAllTopicsView() throws Exception {
		mvc.perform(get("/show-topics")).andExpect(view().name(is("topics")));
	}
	
	@Test
	public void shouldBeOkForAllTopics() throws Exception {
		mvc.perform(get("/show-topics")).andExpect(status().isOk());	
	}
	
	@Test
	public void shouldPutAllTopicsIntoModel() throws Exception {
		Collection<Topic>allTopics = Arrays.asList(topic, anotherTopic);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		mvc.perform(get("/show-topics")).andExpect(model().attribute("topics",  is(allTopics)));
	}
	
	
}
