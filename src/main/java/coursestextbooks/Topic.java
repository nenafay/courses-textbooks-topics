package coursestextbooks;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topic {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
//achieved by Source->generate equals-> only tick id
	//do for each entity.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public Collection<Course> getCourses() {
		return courses;
	}
	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}
	
	@JsonIgnore
	@ManyToMany(mappedBy = "topics")
	private Collection<Course> courses;
	
	public Topic() {
		//default no args constructor required by jpa
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public Topic(String name) {
		this.name = name;
	}



}
