package coursestextbooks;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CourseRestController {
	
	@Resource
	private CourseRepository courseRepo;
	
	@RequestMapping("/courses")
	public Iterable<Course> findAllCourses(){
		return courseRepo.findAll();
		
	}
}
