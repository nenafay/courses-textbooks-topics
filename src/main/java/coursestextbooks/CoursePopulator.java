package coursestextbooks;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CoursePopulator implements CommandLineRunner {
//	
//	private Logger log = LoggerFactory.getLogger(CoursePopulator.class);
//	
	@Resource
	private CourseRepository courseRepo;
	
	@Resource
	private TopicRepository topicRepo;	

	@Resource
	private TextbookRepository textbookRepo;
	
	@Override
	public void run(String...args) throws Exception {
		Topic geometry = topicRepo.save(new Topic("geometry"));
		Topic trig = topicRepo.save(new Topic("trig"));
		Topic calculus = topicRepo.save(new Topic("calculus"));
		Topic literature = topicRepo.save(new Topic("literature"));
		Topic grammar = topicRepo.save(new Topic("grammar"));
		//create new topic item, save it, store saved topic in variable.
		Course math = new Course("Math", "math stuff", geometry, trig, calculus);
		math = courseRepo.save(math);
		Course english =(new Course("English", "words put together... in English", literature, grammar));
		english = courseRepo.save(english);
		textbookRepo.save(new Textbook("Numberstuff", math));
		textbookRepo.save(new Textbook("Grammar & Syntax", english));
		textbookRepo.save(new Textbook ("All the best stories ever", english));
	}
	
}