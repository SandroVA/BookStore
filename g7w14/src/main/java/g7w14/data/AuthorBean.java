/**
 * 
 */
package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author Svetlana Shopova
 * @since 01.03.2014
 * @version 1.0
 *
 */
@Named
@SessionScoped
public class AuthorBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -281329740264616233L;
	private long authorId;
	private String authorFirstName;
	private String authorLastName;
	
	
	public AuthorBean() {
		super();
		authorId= 0l;
		authorFirstName = "";
		authorLastName = "";
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		if(authorFirstName.contains("\'"))
		{
			authorFirstName.replace("\'", "\\'");
		}
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorLastName() {
		
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		
		if(authorLastName.contains("\'"))
		{
			authorLastName.replace("\'", "\\'");
		}
		this.authorLastName = authorLastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authorFirstName == null) ? 0 : authorFirstName.hashCode());
		result = prime * result + (int) (authorId ^ (authorId >>> 32));
		result = prime * result
				+ ((authorLastName == null) ? 0 : authorLastName.hashCode());
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
		AuthorBean other = (AuthorBean) obj;
		if (authorFirstName == null) {
			if (other.authorFirstName != null)
				return false;
		} else if (!authorFirstName.equals(other.authorFirstName))
			return false;
		if (authorId != other.authorId)
			return false;
		if (authorLastName == null) {
			if (other.authorLastName != null)
				return false;
		} else if (!authorLastName.equals(other.authorLastName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuthorBean [authorId=" + authorId + ", authorFirstName="
				+ authorFirstName + ", authorLastName=" + authorLastName + "]";
	}
	
	

}
