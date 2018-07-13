package coursestextbooks;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {
	
	@Resource
	CourseRepository courseRepo;
	
	@Resource
	TopicRepository topicRepo;

	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value = "id")long arbitraryCourseId, Model model) throws CourseNotFoundException {
		Optional<Course> course = courseRepo.findById(arbitraryCourseId);
		//What happens if optional course is there? What if not?
		if(course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course";
		}
		throw new CourseNotFoundException();
		
	}
	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll());
		return("courses");
	}
	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value = "id")long arbitraryTopicId, Model model) throws TopicNotFoundException {
		Optional<Topic> topic = topicRepo.findById(arbitraryTopicId);
		
		if(topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			//data when searching for topic should be tied in with particular courses.
			return "topic";
		}
		throw new TopicNotFoundException();
	}
	@RequestMapping("/show-topics")
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll());
		return("topics");
	}
	
	@RequestMapping("/add-course")
	public String addCourse(String courseName, String courseDescription, String topicName) {
		Topic topic - topicRepo.findByName(topicName);
		Course newCourse = courseRepo.findByName(courseName);
		
		if(newCourse == null) {
			newCourse = new Course(courseName, courseDescription, topic);
			courseRepo.save(newCourse);
		}
		return "redirect:/showCourses";//no spaces pls
	}
	
	@RequestMapping("/delete-course")
	public String deleteCourseByName(String courseName) {
		
		if(courseRepo.findByName(courseName) != null) {
			Course deletedCourse = courseRepo.findByName(courseName);
			courseRepo.delete(deletedCourse);
		}
		
		return "redirect:/showCourses";
	}
	
	@RequestMapping("/del-course")
	public String deleteCourseById(Long courseId) {
		
		courseRepo.deleteById(courseId);
		
		return "redirect:/courses";		
	}

	@RequestMapping("/find-by-topic")
	public String findCourseByTopic(String topicName, Model model) {
		Topic topic = topicRepo.findByName(topicName);
		model.addAttribute("courses", courseRepo.findByTopicsContains(topic));
		return "/topic";	
	}
	
	@RequestMapping("/sort-courses")
	public String sortCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAllByOrderByNameAscending());
		
		return "redirect:/show-courses";
	}
	
}
