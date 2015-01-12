package g7w14.data;

import java.io.Serializable;

/**
 * Category bean representing the category table in database
 * @author Ian Sumanion
 */

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
/**
 * Category bean that represents a category in the category table
 * @author Ian Ozturk
 *
 */

@Named
@SessionScoped
public class CategoryBean implements Serializable {

	
	private static final long serialVersionUID = -3413793622787895742L;
	
	private long id;
	private String name;
	
	
	public CategoryBean() {
		super();
		id=0;
		name="";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryBean other = (CategoryBean) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CategoryBean [id=" + id + ", name=" + name + "]";
	}
	
	

}
